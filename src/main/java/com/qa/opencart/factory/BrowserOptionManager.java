package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserOptionManager {
	
	private Properties prop;
	private ChromeOptions chromeOptions;
	private EdgeOptions edgeOptions;
	
	public BrowserOptionManager(Properties prop) {
		this.prop = prop;
	}
	
	public ChromeOptions getChromeOptions() {
		chromeOptions = new ChromeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			chromeOptions.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			chromeOptions.addArguments("--incognito");
		}
		return chromeOptions;
	}
	
	public EdgeOptions getEdgeOptions() {
		edgeOptions = new EdgeOptions();
		if(Boolean.parseBoolean(prop.getProperty("headless"))) {
			edgeOptions.addArguments("--headless");
		}
		if(Boolean.parseBoolean(prop.getProperty("incognito"))) {
			edgeOptions.addArguments("--inPrivate");
		}
		return edgeOptions;
	}

}
