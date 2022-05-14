package com.qa.opencart.driverfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory
{
    public WebDriver driver;
    public Properties prop;
    public static String highlight;
    public OptionsManager optionsManager;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

    /**
     * This method is used to initialize the driver
     *
     * @param browser
     * @return This will return the driver
     */
    public WebDriver intit_driver(Properties prop)
    {
        String browser = prop.getProperty("browser").trim();
        String browserVersion = prop.getProperty("browserversion").trim();
        // String url = prop.getProperty("url").trim();
        highlight = prop.getProperty("highlight");
        optionsManager = new OptionsManager(prop);
        if (browser.equalsIgnoreCase("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            // driver = new ChromeDriver(optionsManager.getChromeOptions());
            if (Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Running test cases in remote machine
                init_remoteDriver("chrome", browserVersion);
            } else
            {
                // Running test cases in local machine
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            }
            
        } else if (browser.equalsIgnoreCase("firefox"))
        {
            WebDriverManager.firefoxdriver().setup();
            // driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
            if (Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Running test cases in remote machine
                init_remoteDriver("firefox", browserVersion);
            } else
            {
                // Running test cases in the local machine
                tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
            }
        } else if (browser.equalsIgnoreCase("safari"))
        {
            // driver = new SafariDriver();
            tlDriver.set(new SafariDriver());
        } else
        {
            System.out.println(" Please pass the valid browser :" + browser);
        }
        // driver.manage().window().maximize();
        // driver.manage().deleteAllCookies();
        // driver.get(url);
        //
        // return driver;
        // Parallel execution
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        // openUrl(prop.getProperty("url"));
        URL url;
        try
        {
            url = new URL(prop.getProperty("url"));
            openUrl(url);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        
        return getDriver();
    }

    /**
     * @param string
     */
    private void init_remoteDriver(String browser, String browserVersion)
    {
        System.out.println("Running test on remote grid server:" + browser);
        if (browser.equalsIgnoreCase("chrome"))
        {
            // Selenium 3.x code
            // DesiredCapabilities cap = DesiredCapabilities.chrome();
            // cap.setCapability(ChromeOptions.CAPABILITY,
            // optionsManager.getChromeOptions());
            
            // Selenium 4
            
            DesiredCapabilities cap = new DesiredCapabilities();
            // cap.setBrowserName("chrome");
            cap.setCapability("browsername", "chrome");
            cap.setCapability("browserVersion", browserVersion);
            cap.setCapability("enableVNC", true);
            cap.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
            try
            {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        } else if (browser.equalsIgnoreCase("firefox"))
        {
            // Selenium 3.x code
            // DesiredCapabilities cap = DesiredCapabilities.firefox();
            // cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS,
            // optionsManager.getFireFoxOptions());
            // Selenium 4
            DesiredCapabilities cap = new DesiredCapabilities();
            // cap.setBrowserName("firefox");
            cap.setCapability("browsername", "firefox");
            cap.setCapability("browserVersion", browserVersion);
            cap.setCapability("enableVNC", true);
            cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFireFoxOptions());
            try
            {
                tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), cap));
            } catch (MalformedURLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * getDriver() :- it will return the thread local copy of the webdriver
     * syschronized driver
     */
    public static synchronized WebDriver getDriver()
    {
        return tlDriver.get();
    }

    /**
     * This method is used to initialize the properies from the config file
     *
     * @return this will return the prop refernce.
     */
    public Properties inti_Prop()
    {
        prop = new Properties();
        FileInputStream ip = null;
        String envName = System.getProperty("env");// Read from command line
        if (envName == null)
        {
            System.out.println("Runnig on Production Environment");
            try
            {
                ip = new FileInputStream("./src/resources/config/config.properties");
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        } else
        {
            System.out.println(" Running on environment:" + envName);
            try
            {
                switch (envName.toLowerCase())
                {
                    case "qa":
                        ip = new FileInputStream("./src/resources/config/qa.config.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream("./src/resources/config/dev.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream("./src/resources/config/stage.config.properties");
                        break;
                    case "uat":
                        ip = new FileInputStream("./src/resources/config/uat.config.properties");
                        break;
                    default:
                        System.out.println("Please pass the right the environmnet");
                        break;
                }
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            prop.load(ip);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return prop;
    }
    
    /**
     * Take the screen shot
     *
     * @return
     */
    public String getScreenShot()
    {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try
        {
            FileUtils.copyFile(src, destination);
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return path;
    }
    
    /**
     * launch url method
     *
     * @param url
     */
    public void openUrl(String url)
    {
        try
        {
            if (url == null)
            {
                throw new Exception(" Url is null ");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        getDriver().get(url);
    }

    public void openUrl(URL url)
    {
        try
        {
            if (url == null)
            {
                throw new Exception(" Url is null ");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        getDriver().navigate().to(url);
    }

    public void openUrl(String baseUrl, String path)
    {
        try
        {
            if (baseUrl == null)
            {
                throw new Exception(" Url is null ");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // http://amazon.com/accpage/users.html
        getDriver().get(baseUrl + "/" + path);
    }

    public void openUrl(String baseUrl, String path, String queryParam)
    {
        try
        {
            if (baseUrl == null)
            {
                throw new Exception(" Url is null ");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // http://amazon.com/accpage/users.html
        getDriver().get(baseUrl + "/" + path + "?" + queryParam);
    }
}
