package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExelUtil
{
    private static Workbook book;
    private static Sheet sheet;
    public static final String TEST_DATA_SHEET_PATH = ".\\src\\resources\\testdata\\opencarttestdata.xlsx";

    public static Object[][] getTestData(String sheetName)
    {
        Object data[][] = null;
        try
        {
            FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
            book = WorkbookFactory.create(ip);
            sheet = book.getSheet(sheetName);
            
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (InvalidFormatException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int rows = sheet.getLastRowNum(); // -- Rnumber of rows
        int cols = sheet.getRow(0).getLastCellNum();// -- Number of Columns ( Using
        // first row.The columns are fixed.
        System.out.println(rows + " :" + cols);
        data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < sheet.getLastRowNum(); i++)
        {
            for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++)
            {
                data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                // System.out.println(data[i][j]);
            }
        }
        return data;
    }
}
