package com.github.githubbers;

import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOutput implements ExcelData
{
    static void output(XSSFWorkbook workbook, XSSFSheet sheet)
    {
        for (int i = 0; i < title.length; i++)
        {
            sheet.autoSizeColumn(i);
        }

        try (OutputStream fileOut = new FileOutputStream(fileName))
        {
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            
        } catch (Exception e)
        {
            System.err.println("Failed to create or save the Excel file due to unexpected error.");
            System.exit(-1);
        }

    }


}
