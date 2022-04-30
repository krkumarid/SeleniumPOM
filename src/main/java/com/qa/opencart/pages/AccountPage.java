package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ElementUtil;

public class AccountPage
{
    private WebDriver driver;
    private ElementUtil eleUtil;
    
    private By header = By.cssSelector("div#logo a");
    private By accoountSections = By.cssSelector("div#content h2");
    private By searchField = By.name("search");
    private By searchButton = By.cssSelector("div#search button");
    private By logoutLink = By.linkText("Logout");
    
    public AccountPage(WebDriver driver)
    {
        this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }

    // Page Actions

    public String getAccountPageTitle()
    {
        return eleUtil.doGetTitle(ConstantValues.ACCOUONT_PAGE_TITLE, ConstantValues.DEFAULT_TIME_OUT);
    }
    
    public boolean getAccountPageUrl()
    {
        return eleUtil.waitForURLToBe(ConstantValues.ACCOUNT_PAGE_URL, ConstantValues.DEFAULT_TIME_OUT);
    }

    public String getAccountPageHeader()
    {
        return eleUtil.doGetText(header);
    }

    public SearchResultsPage doSearch(String prodName)
    {
        System.out.println("Search the product :" + prodName);
        eleUtil.doSendKeys(searchField, prodName);
        eleUtil.doClick(searchButton);
        return new SearchResultsPage(driver);
    }
    
    public boolean isLogoutLinkExists()
    {
        return eleUtil.doDisplayed(logoutLink);
    }

    public void logout()
    {
        if (isLogoutLinkExists())
        {
            eleUtil.doClick(logoutLink);
        }
    }

    public List<String> getAccountSecList()
    {
        List<WebElement> accSecList = eleUtil.waitForElementsToBeVisible(accoountSections,
                ConstantValues.DEFAULT_TIME_OUT);
        List<String> accSecValues = new ArrayList<String>();
        for (WebElement e : accSecList)
        {
            String text = e.getText();
            accSecValues.add(text);
        }
        return accSecValues;
    }
    
    public boolean isSearchExists()
    {
        return eleUtil.doDisplayed(searchField);
    }
}
