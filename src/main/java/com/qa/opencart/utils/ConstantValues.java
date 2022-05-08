package com.qa.opencart.utils;

import java.util.ArrayList;
import java.util.List;

public class ConstantValues
{
    public static final String LOGIN_PAGE_TITLE = "Account Login";
    public static final String LOGIN_PAGE_URL_FRACTION = "?route=account/login";
    public static final String REGISTER_PAGE_TITLE = "Register Account";
    public static final String REGISTER_PAGE_URL_FRACTION = "route=account/register";
    public static final String ACCOUONT_PAGE_TITLE = "My Account";
    public static final String ACCOUNT_PAGE_URL = "route=account/account";
    public static final String ACCOUNT_PAGE_HEADER = "Your Store";
    public static final String SEARCH_PAGE_TITLE = "Search";
    public static final String SEARCH_PAGE_HEADER = "Search";
    public static final String SEARCH_PAGE_URL = "route=product/search";
    public static final String LOGIN_ERROR_MESSG = "No match for E-Mail Address and/or Password";
    public static final String REGISTER_SUCCESS_MESSSAGE = "Your Account Has Been Created";
    public static final long DEFAULT_TIME_OUT = 5;
    public static final long DEFAULT_POLLING_TIME = 500;
    public static final int IMAC_IMAGE_COUNT = 3;
    public static final int MACBOOKPRO_IMAGE_COUNT = 4;
    public static final int MACBOOKAIR_IMAGE_COUNT = 4;
    public static final String REGISTER_SHEET_NAME = "Registartion";
    public static final String PRODUCT_SHEET_NAME = "Product";
    public static final String TEST_DATA_SHEET_PATH = ".\\src\\resources\\testdata\\opencarttestdata.xlsx";

    public static List<String> getExpectedAccSecList()
    {
        List<String> expSecList = new ArrayList<String>();
        expSecList.add("My Account");
        expSecList.add("My Orders");
        expSecList.add("My Affiliate Account");
        expSecList.add("Newsletter");
        return expSecList;
    }
}
