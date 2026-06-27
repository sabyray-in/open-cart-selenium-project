package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.tagName("h1");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private By productImage = By.cssSelector("ul.thumbnails img");
	private Map<String, String> productMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String productPageHeader = eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println("Product Page Header : " + productPageHeader);
		return productPageHeader;
	}
	
	private void getProductMetaData() {
		List<WebElement> productMetaList = eleUtil.getmultipleElements(productMetaData);
		for (WebElement e : productMetaList) {
			String metaText = e.getText();
			String metaData[] = metaText.split(":");
			String metaKey = metaData[0].trim();
			String metaValue = metaData[1].trim();
			productMap.put(metaKey, metaValue);
		}
		
	}
	
	private void getProductPriceData() {
		List<WebElement> priceMetaList = eleUtil.getmultipleElements(productPriceData);
		String price = priceMetaList.get(0).getText();
		String exTaxPrice = priceMetaList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", price);
		productMap.put("extaxprice", exTaxPrice);
	}
	
	public Map<String, String> getProductData() {
//		productMap = new LinkedHashMap<String, String>(); to maintain the insertion order
//		productMap = new TreeMap<String, String>();
		productMap = new HashMap<String, String>();
		productMap.put("productheader", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		System.out.println("Full Product Map data : " + productMap);
		return productMap;
	}
	
	public int getProductImageCount() {
		int imageCount = eleUtil.waitForMultipleElementsVisible(productImage, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		return imageCount;
		
	}
	
}
