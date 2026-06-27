package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class LoginPageTest extends BaseTest {
	
	@Test
	public void LoginPageTitleTest() {
		String actualTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	@Test
	public void LoginPageURLTest() {
		String actualurl = loginPage.getLoginPageURL();
		Assert.assertTrue(actualurl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isFogotPwdLinkExist());
	}
	@Test
	public void isLogoVisibleTest() {
		Assert.assertTrue(loginPage.isLogoVisible());
	}
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username") , prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	

}
