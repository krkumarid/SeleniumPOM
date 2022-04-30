package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage
{
    // private WebDriver driver;
    private ElementUtil eleUtil;
    
    private By productHeader = By.xpath("//div[@id='content']//h1");
    private By productImages = By.cssSelector("ul.thumbnails img");
    private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
    private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
    private By productQty = By.id("input-quantity");
    private By productAddToCart = By.id("button-cart");
    
    private Map<String, String> productInfoMap;
    
    public ProductInfoPage(WebDriver driver)
    {
        // this.driver = driver;
        eleUtil = new ElementUtil(driver);
    }
    
    public String getProductHeader()
    {
        String productHeaderText = eleUtil.doGetText(productHeader);
        System.out.println(productHeaderText);
        return productHeaderText;
    }
    
    public int getProductImageCount()
    {
        return eleUtil.waitForElementsToBeVisible(productImages, ConstantValues.DEFAULT_TIME_OUT,
                ConstantValues.DEFAULT_POLLING_TIME).size();
        
    }
    
    public Map<String, String> getProductInfo()
    {
        productInfoMap = new LinkedHashMap<String, String>();
        productInfoMap.put("name", getProductHeader());
        getProductMetaData();
        getProductPrice();
        return productInfoMap;
    }
    
    private void getProductMetaData()
    {
        List<WebElement> prodMetaDataList = eleUtil.getElements(productMetaData);
        /**
         * Brand: Apple Product Code: Product 18 Reward Points: 800 Availability: Out Of
         * Stock
         */
        for (WebElement e : prodMetaDataList)
        {
            String text = e.getText();
            String meta[] = text.split(":");
            String metaKey = meta[0].trim();
            String metaValue = meta[1].trim();
            productInfoMap.put(metaKey, metaValue);
            
        }
    }
    
    private void getProductPrice()
    {
        List<WebElement> prodMetaPriceList = eleUtil.getElements(productPriceData);
        // $2,000.00
        // Ex Tax: $2,000.00
        String price = prodMetaPriceList.get(0).getText().trim();
        String exPrice = prodMetaPriceList.get(1).getText().trim();
        productInfoMap.put("price", price);
        productInfoMap.put("ExPrice", exPrice);
    }
}
