package com.github.githubbers;

public interface ExcelData
{
    String fileName = OutputPathFile.getOutputPathFile() + "CKJM.xlsx";
    
    String sheetName = "Students";
    
    String[] title = {"NO.", "MATRIC", "NAME", "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM"};
            }