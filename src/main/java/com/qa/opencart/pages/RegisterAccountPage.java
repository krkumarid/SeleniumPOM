package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.driverfactory.DriverFactory;
import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ElementUtil;

public class RegisterAccountPage extends DriverFactory
{
    // 1.Declare WebDriver
    // private WebDriver driver;
    private ElementUtil eleUtil;
    
    // 2.Register Page Constructor
    public RegisterAccountPage(WebDriver driver)
    {
        // this.driver = driver;
        eleUtil = new ElementUtil(this.driver);
    }
    
    // 3.Declare By locators
    private By pageHeader = By.xpath("//h1[text()='Register Account']");
    
    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");
    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confirmPassword = By.id("input-confirm");
    
    private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
    private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
    
    private By checkBox = By.name("agree");
    private By contnueButton = By.cssSelector("input.btn.btn-primary");
    
    private By successMsg = By.xpath("//div[@id='content']/h1");
    private By logout = By.linkText("Logout");
    private By registerLink = By.linkText("Register");

    public boolean accountRegistration(String firstName, String lastName, String email, String telephone,
            String password, String subscribe)
    {
        eleUtil.doSendKeys(this.firstName, firstName);
        eleUtil.doSendKeys(this.lastName, lastName);
        eleUtil.doSendKeys(this.email, email);
        eleUtil.doSendKeys(this.telephone, telephone);
        eleUtil.doSendKeys(this.password, password);
        eleUtil.doSendKeys(this.confirmPassword, password);

        if (subscribe.equals("yes"))
        {
            eleUtil.doClick(subscribeYes);
        } else
        {
            eleUtil.doClick(subscribeNo);
        }
        eleUtil.doClick(checkBox);
        eleUtil.doClick(contnueButton);
        String strMsg = eleUtil.waitForElementTobeVisibleWithElement(successMsg, ConstantValues.DEFAULT_TIME_OUT,
                ConstantValues.DEFAULT_POLLING_TIME).getText();
        System.out.println(strMsg);
        if (strMsg.contains(ConstantValues.REGISTER_SUCCESS_MESSSAGE))
        {
            eleUtil.doClick(logout);
            eleUtil.doClick(registerLink);
            return true;
        }
        return false;
    }
}
