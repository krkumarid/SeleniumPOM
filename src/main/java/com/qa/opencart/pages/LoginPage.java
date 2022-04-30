package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Epic 100:Desiggn open cart app- Login Page")
@Story("US 101: Open cart  login  design with multiple features ")
public class LoginPage
{
    // 1.Declare private driver
    private WebDriver driver;
    private ElementUtil eleUtil;
    
    // 2.Page Constructor
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        eleUtil = new ElementUtil(this.driver);
    }

    // 3.By Locators
    private By emaiId = By.id("input-email");
    private By password = By.id("input-password");
    private By LoginButton = By.xpath("//input[@value ='Login']");
    private By registerLink = By.linkText("Register");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By warningMessge = By.cssSelector("div.alert.alert-danger.alert-dismissible");

    public By getEmaiId()
    {
        return emaiId;
    }

    public void setEmaiId(By emaiId)
    {
        this.emaiId = emaiId;
    }

    public By getPassword()
    {
        return password;
    }

    public void setPassword(By password)
    {
        this.password = password;
    }

    public By getLoginButton()
    {
        return LoginButton;
    }

    public void setLoginButton(By loginButton)
    {
        LoginButton = loginButton;
    }

    // 4.Page Actions
    @Step("Getting login page title...")
    public String getLoginPageTitle()
    {
        return eleUtil.doGetTitle(ConstantValues.LOGIN_PAGE_TITLE, ConstantValues.DEFAULT_TIME_OUT);
    }

    @Step("Getting login page url...")
    public boolean getLoginPageUrl()
    {
        return eleUtil.waitForURLToBe(ConstantValues.LOGIN_PAGE_URL_FRACTION, ConstantValues.DEFAULT_TIME_OUT);
    }

    @Step("Checking forgot password link exists  or not..")
    public boolean isForgotPwdLinkExists()
    {
        return eleUtil.doDisplayed(forgotPwdLink);
    }

    @Step("Checking register link exists or not...")
    public boolean isRegisterLinkExists()
    {
        return eleUtil.doDisplayed(registerLink);
    }

    @Step("do login with username:{0} and password:{1}")
    public AccountPage doLogin(String userName, String pwd)
    {
        System.out.println("Login with :" + userName + ":" + pwd);
        eleUtil.doSendKeys(emaiId, userName);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(LoginButton);
        return new AccountPage(driver);
    }

    @Step("do login with wrong username:{0} and wrong password:{1}")
    public boolean doLoginWithWrongCredentials(String userName, String pwd)
    {
        System.out.println("Login with wrong credentials:" + userName + ":" + pwd);
        eleUtil.doSendKeys(emaiId, userName);
        eleUtil.doSendKeys(password, pwd);
        eleUtil.doClick(LoginButton);
        String errorMessage = eleUtil.doGetText(warningMessge);
        System.out.println(errorMessage);
        if (errorMessage.contains(ConstantValues.LOGIN_ERROR_MESSG))
        {
            System.out.println("Login is not done....");
            return false;
        }
        return true;
    }

    @Step("Navigating to the registration page")
    public RegisterAccountPage goToRegisterPage()
    {
        eleUtil.doClick(registerLink);
        return new RegisterAccountPage(driver);
    }
}
