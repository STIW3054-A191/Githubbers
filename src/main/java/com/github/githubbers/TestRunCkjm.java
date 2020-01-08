package com.github.githubbers;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.MetricsFilter;

public class TestRunCkjm implements Runnable {
    private String reName, matricNum;
    private CountDownLatch latch;
    private int latchAll;
    private ArrayList<String> unknownMatricNum;
    private PrintStream console;

    public TestRunCkjm(String ReName, String MatricNum, CountDownLatch Latch, int LatchAll, ArrayList<String> UnknownMatricNum, PrintStream Console) {
        this.reName = ReName;
        this.matricNum = MatricNum;
        this.latch = Latch;
        this.latchAll = LatchAll;
        this.unknownMatricNum = UnknownMatricNum;
        this.console = Console;
    }

    @Override
    public void run() {

        final ArrayList<String> classPathArr = ClassCkjm.getPath(reName);

        if (classPathArr.isEmpty()) {
            synchronized (TestRunCkjm.class) {
                System.setErr(console);
                LogOutput.saveFiles(matricNum, reName, "No Class File!");
                OutputResult.print(true, reName, "No Class File!", latch, latchAll);
            }
        } else {

            CkjmOutputHandler outputHandler = new CkjmOutputHandler() {
                int i = 0, wmc = 0, dit = 0, noc = 0, cbo = 0, rfc = 0, lcom = 0;

                @Override
                public void handleClass(String name, ClassMetrics c) {
                    wmc += c.getWmc();
                    dit += c.getDit();
                    noc += c.getNoc();
                    cbo += c.getCbo();
                    rfc += c.getRfc();
                    lcom += c.getLcom();
                    i++;

                    try (FileWriter writer = new FileWriter(Directory.getTextPathFile() + matricNum + ".txt", true)) {

                        writer.write(name + "\n WMC : " + c.getWmc() + ",  DIT : " + c.getDit() + ", NOC : " + c.getNoc() + ", CBO : " + c.getCbo() + ", RFC : " + c.getRfc() + ", LCOM : " + c.getLcom() + "\n");

                        if (i == classPathArr.size()) {
                            writer.write("Total :\n WMC : " + wmc + ", DIT : " + dit + ", NOC : " + noc + ", CBO : " + cbo + ", RFC : " + rfc + ", LCOM : " + lcom + "\n\n");

                            synchronized (TestRunCkjm.class) {
                                CkjmToExcel.addData(matricNum, unknownMatricNum, wmc, dit, noc, cbo, rfc, lcom);
                            }

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            synchronized (TestRunCkjm.class) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                System.setErr(ps);
                String[] stringArray = classPathArr.toArray(new String[0]);
                MetricsFilter.runMetrics(stringArray, outputHandler);
                System.setErr(console);

                if (!baos.toString().equals("")) {
                    LogOutput.saveFiles(matricNum, reName, baos.toString());
                }
            }
            OutputResult.print(false, reName, "Test CKJM Completed !", latch, latchAll);
        }
    }

}
