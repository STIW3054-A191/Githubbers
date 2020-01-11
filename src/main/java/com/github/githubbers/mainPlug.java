package com.github.githubbers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class mainPlug
{

    public static void main(String[] args) throws InterruptedException
    {
        TimeTaken.start();

        if(args.length!=0)
        {
            for (String s : args)
            {
                if (s.endsWith(".class"))
                {
                    TestCkjm.test(s);
                } else
            {
            File dir = new File(s);
        if (dir.isDirectory())
        {
            String pomPath = PomDirectory.getPath(dir);
            if (pomPath != null)
            {
        if (MavenBuild.cleanInstall(pomPath) == 0)
        {
            System.out.println("Maven build completed.");
            System.out.println("\nGoing to test CKJM!");
            ArrayList<String> arrClass = null;
            //ClassPath.findClass(dir);
        if (!arrClass.isEmpty())
        {
            for (String c : arrClass)
            {
                TestCkjm.test(c);
            }
        } else
            {
            System.err.println("No class file detected.");
        }
        System.out.println("\nRunning Jar file!");

        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar " + JarDirectory.getPath(pomPath), null, dir);

            Thread readStream = new Thread(new JarReadOutput.ReadStreamRunnable(proc.getInputStream(), proc.getErrorStream()));
            readStream.start();

            if (proc.waitFor(1, TimeUnit.MINUTES))
            {
                proc.destroy();
            }

        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

        } else
            {
            System.err.println("Build failed.");
        }

        } else
            {
            System.err.println("No pom.xml file detected.");
        }
        } else
            {
            System.err.println("Please enter the correct folder or class path.");
        }
        }
            }
            TimeTaken.endAndOutput();
            System.exit(0);
        }

        System.out.println("Checking Maven home.");
        MavenOrigin.setHome();

        System.out.println("\nChecking folder!\n/target/output/");
        CheckDirectory.checkDir();

        /* Create Excel file and get List Of Students
        System.out.println("\nCreate Excel file and get List Of Students...");
        CreateExcel.create();
        GetListOfStudents.get(); */

        System.out.println("\nCheck total repositories.");
        ArrayList<String> arrLink = repoPlug.getLink();
        int totalRepo = arrLink.size();
        System.out.println("Total Repositories: " + totalRepo);

        System.out.println("\nCheck PC total threads.");
        System.out.format("%-35s: %-20s\n", "PC total threads ", Threads.totalThreads());
        System.out.format("%-35s: %-20s\n", "Total threads use for cloning ", Threads.availableLightThreads());

        System.out.println("\nCloning repositories.");
        CountDownLatch latchCloneRepo = new CountDownLatch(totalRepo);
        ExecutorService execCloneRepo = Executors.newFixedThreadPool(Threads.availableLightThreads());
        for (String link : arrLink)
        {
            Thread threadCloneRepo = new Thread(new CloneRepo(link, totalRepo, latchCloneRepo));
            execCloneRepo.execute(threadCloneRepo);
        }
        execCloneRepo.shutdown();
        latchCloneRepo.await();
        System.out.println("All repositories cloning completed.");

        System.out.println("\n[Compiling] Maven Building Repositories!");
        ArrayList<String[]> buildSuccessRepo = new ArrayList<>();
        CountDownLatch latchMavenCleanInstall = new CountDownLatch(totalRepo);
        ExecutorService execMavenCleanInstall = Executors.newFixedThreadPool(Threads.availableHeavyThreads());
        for (String link : arrLink)
        {
            Thread threadMavenCleanInstall = new Thread(new MavenCompile(link, totalRepo, latchMavenCleanInstall, buildSuccessRepo));
            execMavenCleanInstall.execute(threadMavenCleanInstall);
        }
        execMavenCleanInstall.shutdown();
        latchMavenCleanInstall.await();
        System.out.println("All Maven build completed.");

        System.out.println("\nRunning Jar file.");
        CountDownLatch latchRunJar = new CountDownLatch(buildSuccessRepo.size());
        ExecutorService execRunJar = Executors.newFixedThreadPool(Threads.availableLightThreads());
        for(String[] repoDetails : buildSuccessRepo)
        {
            Thread threadRunJar = new Thread(new JarRun(repoDetails[0], repoDetails[1], repoDetails[2],latchRunJar,buildSuccessRepo.size()));
            execRunJar.execute(threadRunJar);
        }
        execRunJar.shutdown();
        latchRunJar.await();
        System.out.println("All running Jar completed.");

        TimeTaken.endAndOutput();
    }
}
