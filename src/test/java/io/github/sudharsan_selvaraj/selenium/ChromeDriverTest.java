package io.github.sudharsan_selvaraj.selenium;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ChromeDriverTest extends BaseWebDriverTest {

    @BeforeMethod
    public void setupDriver() {
        listener.set(getListener());
        localDriver.set(getChromeDriverSpy(listener.get()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        localDriver.get().quit();
    }

    @Test
    public void testDriverTypeCasting() {
        WebDriver driver = localDriver.get();

        assertTrue(driver instanceof ChromeDriver);
        assertTrue(driver instanceof RemoteWebDriver);
        assertTrue(driver instanceof JavascriptExecutor);
        assertTrue(driver instanceof TakesScreenshot);
    }

}
