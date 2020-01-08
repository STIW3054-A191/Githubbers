package com.github.githubbers;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class CreateExcel implements ExcelData {
    public static void create() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);

        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < title.length; i++) {
            row.getCell(i).setCellStyle(style);
        }
        ExcelOutput.output(workbook, sheet);

    }
}
