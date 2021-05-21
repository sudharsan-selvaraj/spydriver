package io.github.sudharsan_selvaraj.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

import static org.testng.Assert.assertTrue;

public class IosDriverTest extends BaseWebDriverTest {

    @BeforeMethod
    public void setupDriver() throws MalformedURLException {
        listener.set(getListener());
        localDriver.set(getIosDriverSpy(listener.get()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        localDriver.get().quit();
    }

    @Test
    public void testDriverTypeCasting() {
        WebDriver driver = localDriver.get();

        assertTrue(driver instanceof IOSDriver);
        assertTrue(driver instanceof AppiumDriver);
        assertTrue(driver instanceof RemoteWebDriver);
        assertTrue(driver instanceof JavascriptExecutor);
        assertTrue(driver instanceof TakesScreenshot);
    }

}
