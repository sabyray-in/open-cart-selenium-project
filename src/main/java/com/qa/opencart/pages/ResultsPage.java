package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By searchHeader = By.cssSelector("div#content h1");
	private By searchResults = By.cssSelector("div.caption");
	
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getSearchHeader() {
		String searchHeaderValue = eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		return searchHeaderValue;
	}
	
	public int getSearchResultCounts() {
		int searchResultCounts = eleUtil.waitForMultipleElementsVisible(searchResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Search count is : " + searchResultCounts);
		return searchResultCounts;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
}
