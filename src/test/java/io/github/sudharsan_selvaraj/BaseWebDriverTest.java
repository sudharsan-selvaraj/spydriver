package io.github.sudharsan_selvaraj;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseWebDriverTest {

    public static final String USERNAME = "tejiyed_AKj6f9";
    public static final String AUTOMATE_KEY = "oVHodt3VhxTSFa6AB57r";
    protected ThreadLocal<WebDriver> localDriver = new ThreadLocal<>();
    protected ThreadLocal<SpyDriverListener> listener = new ThreadLocal<>();

    @BeforeSuite
    public void initWebDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setupDriver(String browser) throws MalformedURLException {
        listener.set(getListener());
        WebDriver driver;
        switch (browser) {
            case "firefox":
                driver = getFirefoxDriverSpy(listener.get());
                break;
            case "android":
                driver = getAndroidDriverSpy(listener.get());
                break;
            case "ios":
                driver = getIosDriverSpy(listener.get());
                break;
            default:
                driver = getChromeDriverSpy(listener.get());
        }
        localDriver.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (localDriver.get() != null) {
            localDriver.get().quit();
        }
    }

    protected MockDriverListener getListener() {
        return new MockDriverListener();
    }

    protected WebDriver getChromeDriverSpy(SpyDriverListener listener) {
        ChromeOptions options = new ChromeOptions();
        options.setCapability("w3c", true);
        options.addArguments("--headless");
        return getSpyDriver(new ChromeDriver(options), listener);
    }

    protected WebDriver getFirefoxDriverSpy(SpyDriverListener listener) {
        return getSpyDriver(new FirefoxDriver(), listener);
    }

    protected WebDriver getAndroidDriverSpy(SpyDriverListener listener) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability("device", "Samsung Galaxy S10e");
        return getSpyDriver(new AndroidDriver(new URL("https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub"), capabilities), listener);
    }

    protected WebDriver getIosDriverSpy(SpyDriverListener listener) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability("device", "iPhone 12");
        return getSpyDriver(new IOSDriver<>(new URL("https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub"), capabilities), listener);
    }

    protected WebDriver getSpyDriver(WebDriver driver, SpyDriverListener listener) {
        return SpyDriver.spyOn(driver, SpryDriverOptions.builder().listener(listener).build());
    }

    protected void sleep(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (Exception e) {
            //
        }
    }
}
