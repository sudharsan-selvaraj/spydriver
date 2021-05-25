package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OptionsTest extends BaseWebDriverTest {

    @Test(description = "driver.manage() should return proxied RemoteWebdriverOptions object")
    public void optionsTypeCastingTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Options options = driver.manage();

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "manage");
        assertTrue(options.getClass().getSimpleName().contains("RemoteWebDriverOptions$MockitoMock"));
    }
}
