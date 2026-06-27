package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By headersLink = By.cssSelector("div#content h2");
	private By searchField = By.name("search");
	private By searchButtonIcon = By.xpath("//button[@class='btn btn-default btn-lg']");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccountsPageTitle(){
		String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.ACCOUNTS_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("Page Title is : " + title);
		return title;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.isElementDisplayed(logoutLink);
	}
	
	public int getTotalAccountsPageHeders() {
		return eleUtil.waitForMultipleElementsVisible(headersLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headers = eleUtil.waitForMultipleElementsVisible(headersLink, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> headersList = new ArrayList<String>();
		for (WebElement e : headers) {
			String header = e.getText();
			headersList.add(header);
		}
		return headersList;
	}
	
	public ResultsPage doSearch(String searchKey) {
		WebElement searchElement = eleUtil.waitForElementVisible(searchField, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doSendKeysOnElement(searchElement, searchKey);
		eleUtil.doClick(searchButtonIcon);
		return new ResultsPage(driver);
	}
}
