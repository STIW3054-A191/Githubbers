package com.baktajivan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.CyclicBarrier;

//cyclic barrier class begins
public class CloneRepo extends Thread{
    //create barrier
    private int processor = Runtime.getRuntime().availableProcessors();
    CyclicBarrier barrier = new CyclicBarrier(processor);

    //create executor service
    public void Clone() {
        ExecutorService executor = Executors.newCachedThreadPool();
        callTask tsk = new callTask();//call() method -> directory method
        Future<Integer> result = executor.submit(tsk);
        executor.shutdown();
        try {
            Thread.sleep(100);
            CloneRepo athread = new CloneRepo();
            athread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Starting main thread and performing tasks");

        try {
            System.out.println("There are "+result.get()+" repositories need to be downloaded");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("All repositories have been successfully cloned ");
    }
    class callTask implements Callable<Integer> {
        public Integer call() throws Exception {
            System.out.println("The Main Thread Start");
            Thread.sleep(3000);
            String mkDirectoryPath = "D:\\Githubbers_Repo";
            CloneRepo cr = new CloneRepo();
            if (cr.mkDirectory(mkDirectoryPath)) {
                System.out.println(mkDirectoryPath + " The directory have benn successfully created");
            } else {
                System.out.println(mkDirectoryPath + " This directory have been already existed");
            }
            int repoNumber = 0;
            repoPlug gitlink = new repoPlug();
            for (int i = 0; i <= gitlink.getLink().size(); i++)
                repoNumber += 1;
            return repoNumber;
        }
    }
        @Override
        public void run() {
            repoPlug gl = new repoPlug();
            List<String> urlList = new ArrayList<>();
            urlList.addAll(gl.getLink());
            for (int i = 0; i < urlList.size(); i++) {
                try {
                    String full_command;
                    String command = "git clone {}";
                    full_command = command.replace("{}", urlList.get(i));
                    System.out.println("Downloading and cloning the repository");
                    Process p = Runtime.getRuntime().exec(full_command, null, new File("D:\\Githubbers_Repo"));
                    System.out.println(Thread.currentThread().getName() + "waiting at barrier");
                    this.barrier.await();
                } catch (IOException ex) {
                    //Logger.getLogger(CloneRepo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }//run
    //check whether the directory exist
    public boolean mkDirectory(String path){
        File file = null;
        try{
            file = new File(path);
            if(!file.exists()){
                return file.mkdirs();
            }else{
                return false;
            }

        }catch(Exception e){
        }finally{
            file = null;
        }
        return false;
    }
}//end class

//main class method later
/*class executed {
    public static  void main(String[] args) throws Exception {
        *//*Runnable barrierAction = new Runnable() {
            @Override
            public void run() {
                //git clone add here
                System.out.println("BarrierAction 1 executed ");
            }
        };
        CyclicBarrier barrier1 = new CyclicBarrier(2,barrierAction);*//*
        CloneRepo barrierOne = new CloneRepo();
        barrierOne.Clone();

    }
}*/
