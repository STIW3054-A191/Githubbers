package com.github.githubbers;

public interface ExcelData
{
    String fileName = Directory.getOutputPathFile() + "CKJM.xlsx";
    String sheetName = "Students";
    String[] title = {"NO.", "MATRIC", "NAME", "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM"};
            }