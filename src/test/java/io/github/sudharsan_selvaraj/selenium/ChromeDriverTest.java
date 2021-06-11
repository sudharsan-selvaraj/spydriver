package io.github.sudharsan_selvaraj.selenium;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.*;
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


    @Test(description = "Test findElementByName method")
    public void findElementByNameTest() {
        ChromeDriver driver = (ChromeDriver) localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElementByName("q");
        assertTrue(input.getClass().getSimpleName().contains("RemoteWebElement$MockitoMock"), "Actual class:" + input.getClass().getSimpleName());
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "findElementByName");

        input.sendKeys("TestNinja");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getLocator(), By.name("q"));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), input);
    }

}
