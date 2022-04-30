package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.ConstantValues;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design Open Cart App - Login Page")
@Story("US 101 : pen cart login design with multiple features")
@Listeners(TestAllureListener.class) // Screenshot

public class LoginPageTest extends BaseTest
{
    
    @Description("Login page title test")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void loginPageTitleTest()
    {
        String actTitle = loginPage.getLoginPageTitle();
        System.out.println("Page title:" + actTitle);
        Assert.assertEquals(actTitle, ConstantValues.LOGIN_PAGE_TITLE);
    }
    
    @Description("Login page url test")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginPageUrlTest()
    {
        Assert.assertTrue(loginPage.getLoginPageUrl());
    }
    
    @Description("Forgot password link test")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void forgotPwdLinkTest()
    {
        Assert.assertTrue(loginPage.isForgotPwdLinkExists());
    }
    
    @Description("Registration link test")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4, enabled = false) // enabled = false is excluding the test from the execution
    public void registerLinkTest()
    {
        Assert.assertTrue(loginPage.isRegisterLinkExists());
    }
    
    @Description("Login with correct credentials test")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 5)
    public void doLoginTest()
    {
        accountPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        Assert.assertEquals(accountPage.getAccountPageTitle(), ConstantValues.ACCOUONT_PAGE_TITLE);
    }
    
}
