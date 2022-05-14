package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.driverfactory.DriverFactory;
import com.qa.opencart.pages.AccountPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterAccountPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest
{
    WebDriver driver;
    DriverFactory df;
    public LoginPage loginPage;
    public Properties prop;
    AccountPage accountPage;
    SearchResultsPage searchResultPage;
    ProductInfoPage productInfoPage;
    SoftAssert softAssert;
    RegisterAccountPage registerPage;

    @Parameters({ "browser", "browserversion" })
    @BeforeTest
    public void setup(String browser, String browserVersion)
    {
        df = new DriverFactory();
        prop = df.inti_Prop();
        if (browser != null)
        {
            prop.setProperty("browser", browser);
            prop.setProperty("browserversion", browserVersion);
        }
        driver = df.intit_driver(prop);
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearup()
    {
        driver.quit();
    }
}
