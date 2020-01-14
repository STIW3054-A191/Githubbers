package com.github.githubbers;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.github.githubbers.LogOutput;
import com.github.githubbers.CkjmToExcel;
import com.github.githubbers.ClassCkjm;
import com.github.githubbers.OutputPathFile;
import com.github.githubbers.OutputResult;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.MetricsFilter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestRunCkjm implements Runnable
{
    private String repoName, matricNo;
    private CountDownLatch latch;
    private int latchAll;
    private ArrayList<String> unknownMatricNum;
    private PrintStream console;

    public TestRunCkjm(String RepoName, String MatricNum, CountDownLatch Latch, int LatchAll, ArrayList<String> UnknownMatricNum, PrintStream Console)
    {
        this.repoName = RepoName;
        this.matricNo = MatricNum;
        this.latch = Latch;
        this.latchAll = LatchAll;
        this.unknownMatricNum = UnknownMatricNum;
        this.console = Console;
    }

    @Override
    public void run()
    {

        final ArrayList<String> classPathArr = ClassCkjm.getPath(repoName);

        if (classPathArr.isEmpty())
        {
            synchronized (TestRunCkjm.class)
            {
                System.setErr(console);
                LogOutput.saveFiles(matricNo, repoName, "No file detected.");
                OutputResult.print(true, repoName, "No class file detected.", latch, latchAll);
            }

        } else
            {

            CkjmOutputHandler outputHandler = new CkjmOutputHandler()
            {
                int i = 0, wmc = 0, dit = 0, noc = 0, cbo = 0, rfc = 0, lcom = 0;

                @Override
                public void handleClass(String name, ClassMetrics c)
                {
                    wmc += c.getWmc();
                    dit += c.getDit();
                    noc += c.getNoc();
                    cbo += c.getCbo();
                    rfc += c.getRfc();
                    lcom += c.getLcom();
                    i++;

                    try (FileWriter writer = new FileWriter(OutputPathFile.getTxtPathFile() + matricNo + ".txt", true))
                    {
                        writer.write(name + "\n WMC: " + c.getWmc() + ",  DIT: " + c.getDit() + ", NOC: " + c.getNoc() + ", CBO: " + c.getCbo()
                                + ", RFC: " + c.getRfc() + ", LCOM: " + c.getLcom() + "\n");

                        if (i == classPathArr.size())
                        {
                            writer.write("Total :\n WMC: " + wmc + ", DIT: " + dit + ", NOC: " + noc + ", CBO: "
                                    + cbo + ", RFC: " + rfc + ", LCOM: " + lcom + "\n\n");

                            synchronized (TestRunCkjm.class)
                            {
                                CkjmToExcel.addData(matricNo, unknownMatricNum, wmc, dit, noc, cbo, rfc, lcom);
                                
                            }
                        }
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        Logger.getLogger(TestRunCkjm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            synchronized (TestRunCkjm.class)
            {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(byteArrayOutputStream);
                System.setErr(ps);
                String[] stringArray = classPathArr.toArray(new String[0]);
                MetricsFilter.runMetrics(stringArray, outputHandler);
                
                System.setErr(console);
                if (!byteArrayOutputStream.toString().equals(""))
                {
                    LogOutput.saveFiles(matricNo, repoName, byteArrayOutputStream.toString());
                }
            }
            OutputResult.print(false, repoName, "CKJM tested completed.", latch, latchAll);
        }
    }

}
