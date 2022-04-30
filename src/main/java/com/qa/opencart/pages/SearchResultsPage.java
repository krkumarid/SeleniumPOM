package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage
{
    private WebDriver driver;
    private ElementUtil eleUtil;
    
    private By productResult = By.cssSelector("div.caption a");
    
    public SearchResultsPage(WebDriver driver)
    {
        // this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }
    
    public int getProductListCount()
    {
        int resCount = eleUtil.waitForElementsToBeVisible(productResult, ConstantValues.DEFAULT_TIME_OUT,
                ConstantValues.DEFAULT_POLLING_TIME).size();
        System.out.println("The product result count :" + resCount);
        return resCount;
    }
    
    public ProductInfoPage selectProduct(String mainProductName)
    {
        System.out.println("Main prouct name is :" + mainProductName);
        
        List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productResult, ConstantValues.DEFAULT_TIME_OUT,
                ConstantValues.DEFAULT_POLLING_TIME);
        for (WebElement e : searchList)
        {
            String text = e.getText();
            if (text.equals(mainProductName))
            {
                e.click();
                break;
            }
        }
        return new ProductInfoPage(driver);
    }
}
