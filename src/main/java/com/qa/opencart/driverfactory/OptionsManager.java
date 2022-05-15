package com.qa.opencart.driverfactory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager
{
    private Properties prop;
    private ChromeOptions co;
    private FirefoxOptions fo;
    
    public OptionsManager(Properties prop)
    {
        this.prop = prop;
    }

    public ChromeOptions getChromeOptions()
    {
        co = new ChromeOptions();
        co.addArguments("--disable-dev-shm-usage");
        co.addArguments("--no-sandbox");
        if (Boolean.parseBoolean(prop.getProperty("headless")))
        {
            co.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito")))
        {
            co.addArguments("--incognito");
        }
        return co;
    }
    
    public FirefoxOptions getFireFoxOptions()
    {
        fo = new FirefoxOptions();
        fo.addArguments("--disable-dev-shm-usage");
        fo.addArguments("--no-sandbox");
        if (Boolean.parseBoolean(prop.getProperty("headless")))
        {
            co.addArguments("--headless");
        }
        if (Boolean.parseBoolean(prop.getProperty("incognito")))
        {
            co.addArguments("--incognito");
        }
        return fo;

    }
}
