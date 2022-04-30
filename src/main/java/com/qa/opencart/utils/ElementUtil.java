package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.driverfactory.DriverFactory;

public class ElementUtil
{
    private WebDriver driver;
    private JavaScriptUtil jsUtil;
    
    public ElementUtil(WebDriver driver)
    {
        this.driver = driver;
        jsUtil = new JavaScriptUtil(driver);
    }
    
    /**
     * this method is a wrapper method or utility method.
     *
     * @param locator
     * @return
     */
    public WebElement getElement(By locator)
    {
        WebElement element = driver.findElement(locator);
        if (Boolean.parseBoolean(DriverFactory.highlight))
        {
            jsUtil.flash(element);
        }
        return element;
    }
    
    public WebElement getElement(By locator, int timeOut)
    {
        return doPresenceOfElementLocated(locator, timeOut);
    }
    
    public WebElement getElement(String locatorType, String locatroValue)
    {
        return driver.findElement(getBy(locatorType, locatroValue));
    }
    
    public void doSendKeys(By locator, String value)
    {
        doClear(locator);
        getElement(locator).sendKeys(value);
    }
    
    public void doSendKeys(String locatorType, String locatorValue, String value)
    {

        getElement(locatorType, locatorValue).sendKeys(value);
    }
    
    public void doSendKeys(By loactor, String value, int timeOut)
    {
        doClear(loactor);
        doPresenceOfElementLocated(loactor, timeOut).sendKeys(value);
    }
    
    public By getBy(String locatorType, String locatorValue)
    {
        By locator = null;
        switch (locatorType.toLowerCase())
        {
            case "id":
                locator = By.id(locatorValue);
                break;
            case "classname":
                locator = By.className(locatorValue);
                break;
            case "name":
                locator = By.name(locatorValue);
                break;
            case "xpath":
                locator = By.xpath(locatorValue);
                break;
            case "linktext":
                locator = By.linkText(locatorValue);
                break;
            case "partiallinktext":
                locator = By.partialLinkText(locatorValue);
                break;
            case "cssselector":
                locator = By.cssSelector(locatorValue);
                break;
            case "tagname":
                locator = By.tagName(locatorValue);
                break;
            default:
            {
                System.out.println("Please pass the right locator and value......");
                
            }
        }
        
        return locator;
    }
    
    public int getElementsCount(By locator)
    {
        return getElements(locator).size();
    }
    
    public List<WebElement> getElements(By locator)
    {
        return driver.findElements(locator);
    }
    
    public List<String> getElementsTestList(By locator)
    {
        List<WebElement> eleList = getElements(locator);
        List<String> eleTextList = new ArrayList<String>();
        for (WebElement e : eleList)
        {
            String strText = e.getText();
            if (!strText.isEmpty())
            {
                eleTextList.add(strText);
            }
        }
        return eleTextList;
    }
    
    public List<String> getAtrributeList(By loactor, String type)
    {
        List<WebElement> elmAttr = getElements(loactor);
        List<String> strText = new ArrayList<String>();
        for (WebElement e : elmAttr)
        {
            String str = e.getAttribute(type);
            if (!str.isBlank())
            {
                strText.add(str);
            }
        }
        return strText;
    }
    
    public boolean doDisplayed(By loacator)
    {
        return getElement(loacator).isDisplayed();
    }
    
    public String getAttribute(By loactor, String attName)
    {
        String att = getElement(loactor).getAttribute(attName);
        System.out.println(att);
        return att;
    }
    
    public String doGetText(By loactor)
    {
        return getElement(loactor).getText();
    }
    
    public boolean isElementExist(By locator)
    {
        int count = getElementsCount(locator);
        System.out.println(" The number of elements found :" + count);
        if (count >= 1)
        {
            System.out.println("The element is found.." + locator);
            return true;
        } else
        {
            System.out.println("The element is not found.." + locator);
            return false;
        }
    }
    
    public void doClick(By locator)
    {
        getElement(locator).click();
    }

    public void doClick(String locatroType, String locatorValue)
    {
        getElement(locatorValue, locatroType).click();
    }
    
    public void doClick(By locator, int timeOut)
    {
        doPresenceOfElementLocated(locator, timeOut).click();
    }

    public void doClear(By locator)
    {
        getElement(locator).clear();
    }

    /****** Drop down Generic Methods ***************/
    
    public void doDropDownSelectByIndex(By locator, int index)
    {
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
        
    }
    
    public void doDropDownSelectByVisibleText(By locator, String text)
    {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
        
    }
    
    public void doDropDownSelectByValue(By locator, String value)
    {
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
        
    }
    
    public void doSelectDropDownValue(By locator, String value)
    {
        Select select = new Select(getElement(locator));
        
        List<WebElement> optionsList = select.getOptions();
        
        for (WebElement e : optionsList)
        {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value))
            {
                e.click();
                break;
            }
        }
    }
    
    public void selectDropDownValueWithoutSelectClass(By locator, String value)
    {
        List<WebElement> optionList = getElements(locator);
        
        System.out.println(optionList.size());
        
        for (WebElement e : optionList)
        {
            String text = e.getText();
            System.out.println(text);
            if (text.equals(value))
            {
                e.click();
                break;
            }
        }
    }
    
    /**************** Webtable Utility( Static table ) ***********************/
    public void PrintYTable(By rowLocator, By colLocator, String beforePath, String afterPath)
    {
        int nRow = getElements(rowLocator).size();
        int nCol = getElements(colLocator).size();
        
        for (int i = 2; i <= nRow; i++)
        {
            for (int j = 1; j <= nCol; j++)
            {
                String xPath = beforePath + i + afterPath + j + "]";
                // System.out.println(driver.findElement(By.xpath(xPath)).getText());
                System.out.print(doGetText(By.xpath(xPath)) + "||");
            }
            System.out.println();
            
        }
    }
    
    /**************************** ACTIONS Util ******************************/
    
    public void doMoveToElement(By locator)
    {
        
        Actions action = new Actions(driver);
        action.moveToElement(getElement(locator)).build().perform();
        
    }
    
    public void doClickOnChildMenu(By parentLocator, By childMenuLocator) throws InterruptedException
    {
        doMoveToElement(parentLocator);
        Thread.sleep(5000);
        doClick(childMenuLocator);
    }
    
    /******************** Wait Utils **********************************/
    public String doGetTitleWithFraction(String titleFraction, int timeOut)
    {
        if (waitForTitleContains(titleFraction, timeOut))
        {
            return driver.getTitle();
        }
        return null;
    }
    
    public String doGetTitle(String title, int timeOut)
    {
        if (waitForTitleToBe(title, timeOut))
        {
            return driver.getTitle();
        }
        return null;
    }

    public String doGetTitle(String title, long timeOut)
    {
        if (waitForTitleToBe(title, timeOut))
        {
            return driver.getTitle();
        }
        return null;
    }

    public WebElement doPresenceOfElementLocated(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    public WebElement doPresenceOfElementLocated(By locator, int timeout, long pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofMillis(pollingTime));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    public WebElement isElementVisible(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * An expectation for checking that an element is present on the DOM of a page
     * and visible. Visibility means that the element is not only displayed but also
     * has a height and width that isgreater than 0.
     *
     * @param locator
     * @param timeout
     * @param pollingTime
     * @return
     */
    public WebElement isElementVisible(By locator, int timeout, long pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofMillis(pollingTime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementTobeVisibleWithElement(By locator, int timeOut, long pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(pollingTime));
        return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
    }

    public WebElement waitForElementTobeVisibleWithElement(By locator, long timeOut, long pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(pollingTime));
        return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
    }

    /**************** Wait utils for Non-WebElement *******************************/
    public boolean waitForURLToBe(String urlFraction, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.urlContains(urlFraction));
    }

    public boolean waitForURLToBe(String url, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.urlToBe(url));
        
    }
    
    public boolean waitForTitleContains(String titleFraction, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.titleContains(titleFraction));
    }

    public boolean waitForTitleToBe(String title, int timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.titleIs(title));
    }

    public boolean waitForTitleToBe(String title, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.titleIs(title));
    }

    /********************** Alert Wait *****************************/
    public Alert waitForAlert(long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }
    
    public String getAlertText(long timeOut)
    {
        return waitForAlert(timeOut).getText();
    }
    
    public void doAlertAccept(long timeOut)
    {
        waitForAlert(timeOut).accept();
    }
    
    public void getAlertDismiss(long timeOut)
    {
        waitForAlert(timeOut).dismiss();
    }
    
    public void enterAlertText(String text, long timeOut)
    {
        waitForAlert(timeOut).sendKeys(text);
    }
    
    /******************* Frame wait ***************************************/
    public void waitForFrameByName(String nameOrID, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
    }

    public void waitForFrameByIndex(int frameIndex, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameByLocator(By frameLocator, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public void waitForFrameByElement(WebElement frameElement, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }
    
    /****************************** Element Click when ready *********************/
    public void clickElementWhenReady(By locator, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    public void clickElementWhenReady(By locator, long timeOut, long pollTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(pollTime));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /****************************
     * Wait for Element Using Fluent wait
     *****************************/
    public WebElement waitForElementPresentUsingFluentWait(By locator, int timeOut, int pollingTime)
    {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class)
                .withMessage(Errors.ELEMENT_NOT_FOUND_ERROR_MSG);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForElementPresentUsingWebDriverWait(By locator, int timeOut, int pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.pollingEvery(Duration.ofMillis(pollingTime)).ignoring(NoSuchElementException.class)
                .withMessage(Errors.ELEMENT_NOT_FOUND_ERROR_MSG);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForElementsToBeVisible(By locator, long timeOut)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    public List<WebElement> waitForElementsToBeVisible(By locator, long timeOut, long pollingTime)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(pollingTime));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    /********************************* Custom wait ********************************/
    public WebElement retryingElement(By locator, int timeOut)
    {
        WebElement element = null;
        int attempt = 0;

        while (attempt < timeOut)
        {
            try
            {
                element = getElement(locator);
                break;
            } catch (Exception e)
            {
                System.out.println("The element not found in attempt:" + attempt + ":" + locator);
                try
                {
                    Thread.sleep(500);
                } catch (InterruptedException e1)
                {
                    System.out.println("Inside Catch");
                    e1.printStackTrace();
                }
            }
            attempt++;
        }
        if (element == null)
        {
            try
            {
                throw new Exception("Element not found Exception");
            } catch (Exception e)
            {
                System.out.println("The element not found with " + attempt + "attempts");
            }
            
        }
        return element;
    }
    
    public WebElement retryingElement(By locator, int timeOut, long pollingTime)
    {
        WebElement element = null;
        int attempt = 0;

        while (attempt < timeOut)
        {
            try
            {
                element = getElement(locator);
                break;
            } catch (Exception e)
            {
                System.out.println("The element not found in attempt:" + attempt + ":" + locator);
                try
                {
                    Thread.sleep(pollingTime);
                } catch (InterruptedException e1)
                {
                    System.out.println("Inside Catch");
                    e1.printStackTrace();
                }
            }
            attempt++;
        }
        if (element == null)
        {
            try
            {
                throw new Exception("Element not found Exception");
            } catch (Exception e)
            {
                System.out.println("The element not found");
            }
            
        }
        return element;
    }
}
