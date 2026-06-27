package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void ProductInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username") , prop.getProperty("password"));
	}
	
	@Test
	public void productHeaderTest() {
		resultsPage = accPage.doSearch("macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
	}
	
	@Test
	public void productInfoTest() {
		resultsPage = accPage.doSearch("macbook");
		productInfoPage = resultsPage.selectProduct("MacBook Pro");
		Map<String, String> actualProductMap = productInfoPage.getProductData();
		softAssert.assertEquals(actualProductMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualProductMap.get("Reward Points"), "800");
		softAssert.assertEquals(actualProductMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actualProductMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(actualProductMap.get("extaxprice"), "$2,000.00");
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] getProductImageCountData() {
		return new Object[][] {
			{"macbook","MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"canon", "Canon EOS 5D", 3}
		};
	}
	
	@Test(dataProvider = "getProductImageCountData")
	public void imageCountTest(String searchKey, String productName, int imageCount) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductImageCount(), imageCount);
	}
}
