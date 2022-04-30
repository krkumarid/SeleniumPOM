package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil
{
    private WebDriver driver;

    public JavaScriptUtil(WebDriver driver)
    {
        this.driver = driver;
    }

    // Title of the page
    public String getTitleByJS()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.title;").toString();
    }

    // Custom alert message
    public void generateAlert(String message)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('" + message + "')");
    }

    // get Inner Text
    public String getPageInnerText()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.documentElement.innerText;").toString();
    }

    public void refreshBrowserByJS()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    public void drawBorder(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid red'", element);
    }

    /************* Highlight a WebElement ************************/
    public void flash(WebElement element)
    {
        String bgcolor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 10; i++)
        {
            changeColor("rgb(0,200,0)", element);// 1
            changeColor(bgcolor, element);
        }
    }

    private void changeColor(String color, WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.backgroundColor ='" + color + "'", element);
        try
        {
            Thread.sleep(20);
        } catch (Exception e)
        {

        }
    }

    /*************** Clicking aWebElement using a java script ***/
    public void clickElementByJS(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    /************ Scroll Down *******/
    public void scrollPageDown()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo( 0,document.body.scrollHeight)");
    }

    /************ Scroll UP *******/
    public void scrollPageUP()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo( document.body.scrollHeight,0)");
    }

    /************ Scroll Upto a specific height *******/
    public void scrollPageDown(String height)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0," + height + ")");
    }
    
    /***** Scroll into view of a particular element ********/
    public void scrollIntoView(WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView( true)", element);
    }

    //// ********************* Send Key using JS**************/
    public void sendKeysUsingWithID(String ID, String value)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('" + ID + "').value='" + value + "';");
    }
    
    /********* Page loading status *********************/
    
    public void waitForPageLoad()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String loadingStatus = js.executeScript(" return document.readyState;").toString();
        if (loadingStatus.equals("complete"))
        {
            System.out.println("Page is fully loaded");
        } else
        {
            for (int i = 1; i <= 20; i++)
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                loadingStatus = js.executeScript("return document.readyState;").toString();
                System.out.println("Current page status:" + loadingStatus);
                if (loadingStatus.equals("complete"))
                {
                    break;
                }
            }
        }
    }
}
