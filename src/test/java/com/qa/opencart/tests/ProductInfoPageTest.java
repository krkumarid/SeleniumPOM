package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.utils.ConstantValues;

public class ProductInfoPageTest extends BaseTest
{
    @BeforeClass
    public void productInfoSetUp()
    {
        accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test(priority = 1)
    public void productHeaderTest()
    {
        searchResultPage = accountPage.doSearch("MacBook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
    }

    @Test(priority = 2)
    public void productImageCount()
    {
        searchResultPage = accountPage.doSearch("iMac");
        productInfoPage = searchResultPage.selectProduct("iMac");
        Assert.assertEquals(productInfoPage.getProductImageCount(), ConstantValues.IMAC_IMAGE_COUNT);
    }

    @Test(priority = 3)
    public void productInfoTest()
    {
        searchResultPage = accountPage.doSearch("MacBook");
        productInfoPage = searchResultPage.selectProduct("MacBook Pro");
        Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
        actProductInfoMap.forEach((k, v) -> System.out.println(k + ":" + v));
        // Assert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
        // Assert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
        // Assert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
        softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook Pro");
        softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
        softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
        // We must add the below line
        softAssert.assertAll();
    }
}
