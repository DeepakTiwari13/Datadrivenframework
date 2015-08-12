package com.Naukri.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Xls_Reader_Direct {
	public static HSSFRow row;
	public static HSSFCell cell;
	public static int i, j, type;

	public Logger Framework_Logs = Logger.getLogger("Xls_Reader_Direct");
	private Xls_Reader_Direct datatable;

	public Xls_Reader_Direct getInstance() {
		Framework_Logs.info("In side Xls_Reader_Direct single ton class");

		if (datatable == null) {
			datatable = new Xls_Reader_Direct();
		}
		return datatable;
	}
	public Object[][] getexceldata() throws IOException {
		Framework_Logs.info("In side xls reader utility");

		FileInputStream file = new FileInputStream(new File(
				System.getProperty("user.dir")
						+ "\\src\\com\\Framework\\config\\TestController.xls"));
		Framework_Logs.info("Test controller file is located");
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int numofrows = sheet.getLastRowNum() + 1;
		Framework_Logs.info("Printing number of rows " + numofrows);
		int numofcolumns = sheet.getRow(0).getLastCellNum();
		Framework_Logs.info("Printing number of column " + numofcolumns);
		Object data[][] = new Object[numofrows-1][numofcolumns];

		for (i = 1; i < numofrows; i++) {
			row = sheet.getRow(i);
			for (j = 0; j < numofcolumns; j++) {
				cell = row.getCell(j);
				int type = cell.getCellType();

				if (type == HSSFCell.CELL_TYPE_STRING) {
					Framework_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = STRING; Value = "
							+ cell.getRichStringCellValue().toString());
					data[i-1][j] = cell.getRichStringCellValue().toString();
					Framework_Logs.info(data[i-1][j]);
				} else if (type == HSSFCell.CELL_TYPE_NUMERIC) {
					Framework_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = NUMERIC; Value = "
							+ String.valueOf(cell.getNumericCellValue()));
					data[i-1][j] = String.valueOf(cell.getNumericCellValue());
					Framework_Logs.info(data[i-1][j]);
				} else if (type == HSSFCell.CELL_TYPE_BOOLEAN) {
					Framework_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = BOOLEAN; Value = "
							+ cell.getBooleanCellValue());
				} else if (type == HSSFCell.CELL_TYPE_BLANK) {
					Framework_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = BLANK CELL");
				}

			}

		}
		return data;
		

	}
	public static void main(String args[]) throws IOException{
		Xls_Reader_Direct datatable =  new Xls_Reader_Direct();
		datatable.getexceldata();
	}

}
