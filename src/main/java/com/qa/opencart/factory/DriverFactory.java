package com.qa.opencart.factory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	WebDriver driver;
	Properties prop;
	public static String isHighlight;
	public static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * This method is responsible for initialize the the driver
	 * @param browserName
	 * @return it returns a thread local type of driver
	 */
	public WebDriver initDriver(Properties prop) {
		
		isHighlight = prop.getProperty("highlight");
		BrowserOptionManager boManager = new BrowserOptionManager(prop);
		String browserName = prop.getProperty("browser");
		System.out.println("Browser Name : " + browserName);
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(boManager.getChromeOptions());
			threadLocalDriver.set(new ChromeDriver(boManager.getChromeOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(boManager.getEdgeOptions());
			threadLocalDriver.set(new EdgeDriver(boManager.getEdgeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver();
			threadLocalDriver.set(new FirefoxDriver());
			break;
		default:
			System.out.println(AppError.INVALID_BROWSER_MESSAGE + browserName + " : is an invalid browser");
			throw new BrowserException(AppError.INVALID_BROWSER_MESSAGE);
		}
		getThreadLocalDriver().manage().window().maximize();
		getThreadLocalDriver().manage().deleteAllCookies();
		getThreadLocalDriver().get(prop.getProperty("url"));
		
		return getThreadLocalDriver();
	}
	
	/**
	 * By calling this method you will get a driver which is registered with the ThreadLocal
	 * @return it returns thread local type of driver
	 */
	public static WebDriver getThreadLocalDriver() {
		return threadLocalDriver.get();
	}
	
	/**
	 * This method is responsible for reading all the properties from config.properties file
	 * @return
	 */
	// mvn clean install -Denv="qa"

		public Properties initProperties() {
			prop = new Properties();
			FileInputStream ip = null;

			String envName = System.getProperty("env");
			System.out.println("running tests on env: " + envName);

			try {
				if (envName == null) {
					System.out.println("env is null....hence running tests on QA env");
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				} else {
					switch (envName.toLowerCase().trim()) {
					case "qa":
						ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
						break;
					case "dev":
						ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
						break;
					case "stage":
						ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
						break;
					case "prod":
						ip = new FileInputStream("./src/test/resources/config/config.properties");
						break;

					default:
						System.out.println("plz pass the right env name..." + envName);
						throw new FrameworkException("INVALID ENV NAME");
					}
				}

				prop.load(ip);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return prop;
		}
		
		
		public static String getScreenshot(String methodName) {
			File srcFile = ((TakesScreenshot) getThreadLocalDriver()).getScreenshotAs(OutputType.FILE);// temp dir
			String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis() + ".png";
			File destination = new File(path);
			try {
				FileHandler.copy(srcFile, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return path;
		}
}
