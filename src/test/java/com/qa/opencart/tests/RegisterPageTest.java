package com.qa.opencart.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void registerPageSetUp() {
		registerPage = loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {
		return "selenium-student" + System.currentTimeMillis() + "@opencart.com";
	}
	
	@Test
	public void userRegisterTest() {
		registerPage.userRegisteration("Juna", "Garh", getRandomEmail(), "9219219210", "juna@123", "yes");
	}

}
