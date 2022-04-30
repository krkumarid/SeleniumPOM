package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ExelUtil;

public class RegisterAccountPageTest extends BaseTest
{
    @BeforeClass
    public void setUp()
    {
        registerPage = loginPage.goToRegisterPage();
    }
    
    /**
     * This function generate the random email id
     *
     * @return random email
     */
    public String getRandomEmail()
    {
        Random randomGenerator = new Random();
        String email = "mySelenium" + randomGenerator.nextInt(1000) + "@gmail.com";
        return email;
    }
    
    @DataProvider
    public Object[][] getRegisterData()
    {
        return ExelUtil.getTestData(ConstantValues.REGISTER_SHEET_NAME);
    }

    @Test(dataProvider = "getRegisterData")
    public void doRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe)
    {
        Assert.assertTrue(
                registerPage.accountRegistration(firstName, lastName, getRandomEmail(), telephone, password,
                        subscribe));
    }
}
