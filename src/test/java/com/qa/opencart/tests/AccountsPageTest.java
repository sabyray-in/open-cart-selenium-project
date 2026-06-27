package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest{
	
	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test
	public void accPageTitleTest() {
		String actualTitle = accPage.getAccountsPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accPageHeaderCountTest() {
		Assert.assertEquals(accPage.getTotalAccountsPageHeders(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersTest() {
		List<String> headerList = accPage.getAccPageHeaders();
		Assert.assertEquals(headerList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getSearchKey() {
		return new Object[][] {
			{"macbook" , 3},
			{"imac" , 1},
			{"samsung", 2}
		};
	}
	
	@Test(dataProvider = "getSearchKey")
	public void searchCountTest(String searchKey, int searchCount) {
		resultsPage = accPage.doSearch(searchKey);
		Assert.assertEquals(resultsPage.getSearchResultCounts(), searchCount);
	}
	
	@DataProvider
	public Object[][] getSearchDataForProductSearch() {
		return new Object[][] {
			{"macbook" , "MacBook"},
			{"macbook" , "MacBook Air"},
			{"macbook" , "MacBook Pro"},
			{"imac" , "iMac"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	
	@Test(dataProvider = "getSearchDataForProductSearch")
	public void searchTest(String searchKey, String productName) {
		resultsPage = accPage.doSearch(searchKey);
		productInfoPage = resultsPage.selectProduct(productName);
		Assert.assertEquals(productInfoPage.getProductHeader(), productName);
	}

}
