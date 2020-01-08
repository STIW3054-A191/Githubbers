package com.github.githubbers;

public interface ExcelData {
    String fileName = Directory.getOutputPathFile() + "CKJM.xlsx";
    String sheetName = "ListOfStudents";
    String[] title = {"No.", "Matric", "Name", "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM"};
}
