package com.Naukri.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasicUtil {

	static Logger Naukri_Logs = Logger.getLogger("BasicUtil");

	public void Getscreenshot(String Testcase, WebDriver dr) throws IOException {
		Naukri_Logs.info("Accessing screenshot");
		File scrFile = (File) ((TakesScreenshot) dr)
				.getScreenshotAs(OutputType.FILE);
		Naukri_Logs.info("Saving screenshot to disc");
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")
				+ "\\src\\com\\Framework\\screenshot\\" + Testcase + ".jpg"));

	}

	public int Getserverresponse(String serverurl) throws IOException {

		Naukri_Logs.info("Getting server response");
		URL url = new URL(serverurl);
		HttpURLConnection openConnection = (HttpURLConnection) url
				.openConnection();
		openConnection.connect();
		int rCode = openConnection.getResponseCode();
		return rCode;
	}

	public void Getcroppedimage(String TestCase, String xpathkey, WebDriver dr,
			Properties OR) throws IOException {
		
		Naukri_Logs.info("Taking cropped image");
		File screenshot = ((TakesScreenshot) dr)
				.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);
		WebDriverWait wait = new WebDriverWait(dr, 30);
		WebElement element = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath(OR.getProperty(xpathkey))));
		Point point = element.getLocation();
		int eleWidth = element.getSize().getWidth();
		int eleHeight = element.getSize().getHeight();
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(),
				point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "jpg", screenshot);
		FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir")
				+ "\\src\\com\\Framework\\screenshot\\" + TestCase + ".jpg"));

	}

	public static void dofolderzip() {
		try {
			Naukri_Logs.info("Ziping folder");
			File inFolder = new File(System.getProperty("user.dir")
					+ "\\XSLT_Reports\\output");
			File outFolder = new File("Reports.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data = new byte[1000];
			String files[] = inFolder.list();
			for (int i = 0; i < files.length; i++) {
				in = new BufferedInputStream(new FileInputStream(
						inFolder.getPath() + "/" + files[i]), 1000);
				out.putNextEntry(new ZipEntry(files[i]));
				int count;
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
