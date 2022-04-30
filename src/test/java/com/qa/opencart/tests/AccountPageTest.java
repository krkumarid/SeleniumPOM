package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.ConstantValues;
import com.qa.opencart.utils.Errors;
import com.qa.opencart.utils.ExelUtil;

public class AccountPageTest extends BaseTest
{
    @BeforeClass
    public void accoutPageSetUp()
    {
        accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
    }

    @Test(priority = 1)
    public void accPageTitleTest()
    {
        String actTitle = accountPage.getAccountPageTitle();
        System.out.println(" Account page title:" + actTitle);
        Assert.assertEquals(actTitle, ConstantValues.ACCOUONT_PAGE_TITLE);
    }

    @Test(priority = 2)
    public void accPageHeaderTest()
    {
        String accHeader = accountPage.getAccountPageHeader();
        System.out.println("Account page header :" + accHeader);
        Assert.assertEquals(accHeader, ConstantValues.ACCOUNT_PAGE_HEADER,
                Errors.ACCOUNT_PAGE_HEADER_NOT_FOUND_ERROR_MSG);
    }

    @Test(priority = 3)
    public void isLogoutExistTest()
    {
        Assert.assertTrue(accountPage.isLogoutLinkExists());
    }

    @Test(priority = 4)
    public void accPageSecTest()
    {
        List<String> actAccSecList = accountPage.getAccountSecList();
        // Compare two List
        Assert.assertEquals(actAccSecList, ConstantValues.getExpectedAccSecList());
    }

    @DataProvider
    public Object[][] productData()
    {
        return new Object[][] { { "MacBook Pro" }, { "Apple" }, { "Samsung" } };
    }
    
    @Test(priority = 5, dataProvider = "productData")
    public void searchTest(String productName)
    {
        searchResultPage = accountPage.doSearch(productName);
        Assert.assertTrue(searchResultPage.getProductListCount() > 0);
    }

    @DataProvider
    public Object[][] productSelectData()
    {
        return new Object[][] { { "MacBook", "MacBook Pro" }, { "iMac", "iMac" }, { "Apple", "Apple Cinema 30\"" },
                { "Samsung", "Samsung SyncMaster 941BW" } };
    }

    @DataProvider
    public Object[][] getTestData()
    {
        return ExelUtil.getTestData(ConstantValues.PRODUCT_SHEET_NAME);
    }

    @Test(priority = 6, dataProvider = "getTestData")
    public void selectProductTest(String productName, String mainProductName)
    {
        searchResultPage = accountPage.doSearch(productName);
        productInfoPage = searchResultPage.selectProduct(mainProductName);
        Assert.assertEquals(productInfoPage.getProductHeader(), mainProductName);
    }
}
