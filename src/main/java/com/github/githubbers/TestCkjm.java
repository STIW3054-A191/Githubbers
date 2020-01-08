package com.github.githubbers;

import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.MetricsFilter;

public class TestCkjm
{
    public static void test(String ClassCkjm)
    {
        CkjmOutputHandler outputHandler = (name, c) -> System.out.println(name + "\n WMC : " + c.getWmc() + ",  DIT : " + c.getDit() + ", NOC : " + c.getNoc() + ", CBO : " + c.getCbo() + ", RFC : " + c.getRfc() + ", LCOM : " + c.getLcom() + "\n");

        MetricsFilter.runMetrics(new String[]{ClassCkjm}, outputHandler);
    }
}
