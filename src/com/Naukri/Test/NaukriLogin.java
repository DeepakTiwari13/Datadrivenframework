package com.Naukri.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Naukri.Selenium.Layer.Keywords;
import com.Naukri.util.ErrorUtil;

public class NaukriLogin {
	public static HSSFRow row;
	public static HSSFCell cell;
	public static int i, j, type;

	Logger Naukri_Logs = Logger.getLogger("NaukriLogin");

	@Test(dataProvider = "getdata")
	public void profiledailyupdate(String testcasename, String username,
			String password, String browser, String flag) throws Exception {

		if (flag.equals("N"))
			throw new SkipException("Skipping test because of flag is set to N");

		Naukri_Logs.info("Inside naukri login function");
		Keywords session = new Keywords().getinstance();
		session.openbrowser(browser);
		session.loadurl("Testurl");
		session.clickonelement("LoginButton");
		session.clickonelement("SelectEmailID");
		session.senddata("UserEmailID", username);
		session.senddata("UserPassword", password);
		session.clickonelement("ActualLoginButton");
				
		try {
			Assert.assertTrue(session.comparepagetitle(),
					"Page title is not matching ");
		} catch (Throwable t) {
			Naukri_Logs.info("In the error basket");
			ErrorUtil.addVerificationFailure(t);
		}
		
		session.clickonelement("Viewandupdateprofile");
		session.clickonelement("Addskills");
		session.clickonelement("Saveskills");
		Naukri_Logs.info("Activity output "+session.Waitandperform("Updatemessage").getText());
		session.Mousehover("Logoutpopup");
		session.clickonelement("Pagelogout");
		session.actionaftertest();		
		
	
	}
	@DataProvider
	public Object[][] getdata() throws IOException {
		Naukri_Logs.info("Inside data provider");
		Naukri_Logs.info("In side xls reader utility");

		FileInputStream file = new FileInputStream(new File(
				System.getProperty("user.dir")
						+ "\\src\\com\\Naukri\\config\\TestController.xls"));
		Naukri_Logs.info("Test controller file is located");
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int numofrows = sheet.getLastRowNum() + 1;
		Naukri_Logs.info("Printing number of rows " + numofrows);
		int numofcolumns = sheet.getRow(0).getLastCellNum();
		Naukri_Logs.info("Printing number of column " + numofcolumns);
		Object data[][] = new Object[numofrows - 1][numofcolumns];

		for (i = 1; i < numofrows; i++) {
			row = sheet.getRow(i);
			for (j = 0; j < numofcolumns; j++) {
				cell = row.getCell(j);

				int type = cell.getCellType();

				if (type == HSSFCell.CELL_TYPE_STRING) {
					Naukri_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = STRING; Value = "
							+ cell.getRichStringCellValue().toString());
					data[i - 1][j] = cell.getRichStringCellValue().toString()
							.trim();
					Naukri_Logs.info(data[i - 1][j]);
				} else if (type == HSSFCell.CELL_TYPE_NUMERIC) {
					Naukri_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = NUMERIC; Value = "
							+ String.valueOf(cell.getNumericCellValue()));
					data[i - 1][j] = String.valueOf(cell.getNumericCellValue());
					Naukri_Logs.info(data[i - 1][j]);
				} else if (type == HSSFCell.CELL_TYPE_BOOLEAN) {
					Naukri_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = BOOLEAN; Value = "
							+ cell.getBooleanCellValue());
				} else if (type == HSSFCell.CELL_TYPE_BLANK) {
					Naukri_Logs.info("[" + cell.getRowIndex() + ", "
							+ cell.getColumnIndex() + "] = BLANK CELL");
				}

			}

		}
		return data;

	}

}
