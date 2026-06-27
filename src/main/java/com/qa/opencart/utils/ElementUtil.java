package com.qa.opencart.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
		
		private WebDriver driver;
		private JavaScriptUtil jsUtil;
		
		public ElementUtil(WebDriver driver){
			this.driver = driver;
			jsUtil = new JavaScriptUtil(driver);
		}
		
		public void doSendKeys(By locator, String value) {
			getElement(locator).sendKeys(value);
		}
		
		public void doSendKeysOnElement(WebElement element, String value) {
			element.clear();
			element.sendKeys(value);
		}
		
		private void checkElementHighlight(WebElement element) {
			if(Boolean.parseBoolean(DriverFactory.isHighlight)) {
				jsUtil.flash(element);
			}
		}
		
		public WebElement getElement(By locator) {
			WebElement element  = driver.findElement(locator);
			checkElementHighlight(element);
			return element;
		}
		
		public List<WebElement> getmultipleElements(By locator) {
			return driver.findElements(locator);
		}
		
		public void doClick(By locator) {
			getElement(locator).click();
		}
		
		public boolean isElementDisplayed(By locator) {
			try {
				return getElement(locator).isDisplayed();
			} catch (NoSuchElementException e) {
				System.out.println("Element is not displayed " + locator);
				return false;
			}
		}
		
		//=================================Elements with Wait=======================================
		
		/**
		 * this method wait for the web element to be visible in the page dom
		 * @param locator
		 * @param timeout
		 * @return WebElement
		 */
		public WebElement waitForElementVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			checkElementHighlight(element);
			return element;
		}
		
		/**
		 * this method wait for the element to be enabled and then click on it
		 * @param locator
		 * @param timeout
		 */
		public void waitForElementAndClick(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		}
		
		/**
		 * An expectation for checking that all elements present on the web page that match the locators are visible.
		 * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
		 * @param locator
		 * @param timeout
		 * @return
		 */
		public List<WebElement> waitForMultipleElementsVisible(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * An expectation for checking that there is at least one element present(visible) on a web page.
		 * @param locator
		 * @param timeout
		 * @return
		 */
		public List<WebElement> waitForMultipleElementsPresence(By locator, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		
		/**
		 * This method expects full page title and timeout in seconds and return page title with wait and exception handling
		 * @param expectedFullTitle
		 * @param timeout
		 * @return page title on success otherwise return -1
		 */
		public String getPageTitleIs(String expectedFullTitle, int timeout) {
			if(waitForTitleIs(expectedFullTitle, timeout)) {
				return driver.getTitle();
			} else {
				return "-1";
			}
		}
		
		/**
		 * This method expects partial or fraction page title and timeout in seconds and return full page title with wait and exception handling
		 * @param fractionTitle
		 * @param timeout
		 * @return full page title on success otherwise return -1
		 */
		public String getPageTitleContains(String fractionTitle, int timeout) {
			if(waitForTitleContains(fractionTitle, timeout)) {
				return driver.getTitle();
			} else {
				return "-1";
			}
		}
		
		/**
		 * This method is invoked by getPageTitleIs() internally
		 * @param expectedFullTitle
		 * @param timeout
		 * @return a boolean
		 */
		public boolean waitForTitleIs(String expectedFullTitle, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			boolean flag = false;
			try {
				return wait.until(ExpectedConditions.titleIs(expectedFullTitle));
			} catch (TimeoutException e) {
				System.out.println("Title is not matched");
				return flag;
			}
		}
		
		/**
		 * This method is invoked by getPageTitleContains() internally
		 * @param fractionTitle
		 * @param timeout
		 * @return a boolean value
		 */
		public boolean waitForTitleContains(String fractionTitle, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			boolean flag = false;
			try {
				return wait.until(ExpectedConditions.titleContains(fractionTitle));
			} catch (TimeoutException e) {
				System.out.println("Title is not matched");
				return flag;
			}
		}
		
		/**
		 * This method expects partial title and timeout in seconds
		 * @param fractionTitle
		 * @param timeout
		 * @return on success returns full page title, on failure returns -1 as a String
		 */
		public String waitForTitleContainsAndReturn(String fractionTitle, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				wait.until(ExpectedConditions.titleContains(fractionTitle));
				return driver.getTitle();
			} catch (TimeoutException e) {
				System.out.println("Title is not matched");
				return "-1";
			}
		}
		
		/**
		 * This method expects a fractional or partial URL and timeout in seconds. It has the proper wait
		 * @param fractionURL
		 * @param timeout
		 * @return full page URL
		 */
		public String getPageURLContains(String fractionURL, int timeout) {
			if(waitForURLContains(fractionURL, timeout)) {
				return driver.getCurrentUrl();
			} else {
				return "-1";
			}
		}
		
		/**
		 * this method is invoked by getPageURLContains() internally
		 * @param fractionURL
		 * @param timeout
		 * @return a boolean
		 */
		public boolean waitForURLContains(String fractionURL, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			boolean flag = false;
			try {
				return wait.until(ExpectedConditions.urlContains(fractionURL));
			} catch (TimeoutException e) {
				System.out.println("URL is not matched");
				return flag;
			}
		}
		
		/**
		 * This method expects a partial URL and returns full URL
		 * @param fractionURL
		 * @param timeout
		 * @return on success returns full URL on failure returns -1
		 */
		public String waitForURLContainsAndReturn(String fractionURL, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			try {
				wait.until(ExpectedConditions.urlContains(fractionURL));
				return driver.getCurrentUrl();
			} catch (TimeoutException e) {
				System.out.println("URL is not matched");
				return "-1";
			}
		}
		
		/**
		 * This method wait for multiple tabs or windows
		 * @param noOfWindowsOrTab
		 * @param timeout
		 * @return true or on failure throws TimeoutException
		 */
		public boolean waitForNewWindowOrTab(int noOfWindowsOrTab, int timeout) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

			try {
				wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindowsOrTab));
				return true;
			} catch (TimeoutException e) {
				System.out.println("Number of windows or tabs are not matched..");
			}
			return false;
		}
		
		/**
		 * wait for element visible on the page with fluent wait features
		 * @param locator
		 * @param timeOut
		 * @param pollingTime
		 * @return
		 */
		public WebElement waitForElementVisibleWithFluentFeeatures(By locator, int timeOut, int pollingTime) {		
			Wait<WebDriver> wait =	new FluentWait<WebDriver>(driver)
										.withTimeout(Duration.ofSeconds(timeOut))
										.pollingEvery(Duration.ofSeconds(pollingTime))
										.ignoring(NoSuchElementException.class)
										.ignoring(StaleElementReferenceException.class)
										.ignoring(ElementNotInteractableException.class)
										.withMessage("***** element is not found *****" + locator);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));									
		}
		

		

}
