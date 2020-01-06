package com.github.githubbers;
import java.util.concurrent.CountDownLatch;

public class OutputResult
{

    public static void print(boolean Error, String ReName, String Comment, CountDownLatch Latch, int LatchAll)
    {

        synchronized (OutputResult.class)
        {

            if (Error)
            {
                System.err.format("%-10s %-40s %-20s\n",
                        LatchAll - Latch.getCount() + 1 + "/" + LatchAll,
                        ReName,
                        Comment);
            } else
                {
                System.out.format("%-10s %-40s %-20s\n",
                        LatchAll - Latch.getCount() + 1 + "/" + LatchAll,
                        ReName,
                        Comment);

            }
            Latch.countDown();
        }
    }
}


