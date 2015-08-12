package com.Naukri.Selenium.Layer;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

public class Keywords {

	Logger Naukri_logs = Logger.getLogger("Keywords");
	WebDriver driver = null;
	Properties OR = null;
	FileInputStream Fin = null;
	private Keywords session;

	public Keywords() throws Exception {
		Naukri_logs.info("Inside Keywords constructor");
		OR = new Properties();
		Fin = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\com\\Naukri\\config\\or.properties");
		OR.load(Fin);
	}

	public Keywords getinstance() throws Exception {
		Naukri_logs.info("Initializing single ton class");
		if (session == null) {
			session = new Keywords();
		}
		return session;
	}

	public void openbrowser(String browsername) {
				
		Naukri_logs.info("Inside browser function");
		if (browsername.equals("Mozilla")) {
			Naukri_logs.info("Launching mozilla browser");
			driver = new FirefoxDriver();
		} else if (browsername.equals("Chrome")) {
			System.setProperty(
					"webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "\\src\\com\\Naukri\\drivers\\chromedriver.exe");
			Naukri_logs.info("Launching chrome browser");
			driver = new ChromeDriver();
		} else if (browsername.equals("IE")) {
			System.setProperty(
					"webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "\\src\\com\\Naukri\\drivers\\IEDriverServer.exe");
			Naukri_logs.info("Launching IE browser");
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void loadurl(String Testurl) {
		Naukri_logs.info("Loading url");
		driver.get(OR.getProperty(Testurl));
	}

	public void clickonelement(String xpathkey) {
		Naukri_logs.info("Clicking on element " + xpathkey);
		driver.findElement(By.xpath(OR.getProperty(xpathkey))).click();
	}

	public void senddata(String xpathkey, String data) {
		Naukri_logs.info("Calling send keys with " + data);
		driver.findElement(By.xpath(OR.getProperty(xpathkey))).sendKeys(data);
	}

	public void actionaftertest() {
		Naukri_logs.info("Performing after test activity");
		driver.quit();
		driver=null;

	}

	public boolean comparepagetitle() {
		Naukri_logs.info("Comparing page title");
		String CurrentTitle = driver.getTitle();
		Naukri_logs.info("Current page title is "+CurrentTitle);
		String ExpectedTitle = OR.getProperty("PageTitle");

		if (CurrentTitle.equals(ExpectedTitle)) {
			return true;
		}
		return false;

	}

	public boolean iselementexist(String xpathkey) {
		int size = driver.findElements(By.xpath(OR.getProperty(xpathkey)))
				.size();
		if (size > 0) {
			Naukri_logs.info("Element exist [] "+xpathkey);
			return true;
		}
		return false;

	}

	public WebElement Waitandperform(String xpathkey)
			throws InterruptedException {
		Thread.sleep(5000l);
		int i = 0;

		while (!driver.findElement(By.xpath(OR.getProperty(xpathkey)))
				.isEnabled()) {
			Thread.sleep(5000l);
			i++;
			Naukri_logs.info("Displaying counter " + i);
			Naukri_logs.info("Inside is element enabled loop " + xpathkey);

		}
		while (!driver.findElement(By.xpath(OR.getProperty(xpathkey)))
				.isDisplayed()) {
			Thread.sleep(5000l);
			i++;
			Naukri_logs.info("Displaying counter " + i);
			Naukri_logs.info("Inside is element displayed loop " + xpathkey);

		}
		while (!(driver.findElements(By.xpath(OR.getProperty(xpathkey))).size() > 0)) {
			Thread.sleep(5000l);
			i++;
			Naukri_logs.info("Displaying counter " + i);
			Naukri_logs.info("Inside is element exist loop " + xpathkey);

		}
		
		Naukri_logs.info("Returning element " + xpathkey);
		i = 0;
		return driver.findElement(By.xpath(OR.getProperty(xpathkey)));

	}
	public void Mousehover(String xpathkey){
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(By.xpath(OR.getProperty(xpathkey)));
		action.moveToElement(we).build().perform();
		
	}
}
