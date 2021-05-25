package io.github.sudharsan_selvaraj.common.options;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LogsTest extends BaseWebDriverTest {

    @Test(description = "driver.manage().logs() should return proxied RemoteLogs object")
    public void logsCastingTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        Logs logs = driver.manage().logs();

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "logs");
        assertTrue(logs.getClass().getSimpleName().contains("RemoteLogs$MockitoMock"),
                "Actual Class Name:" + logs.getClass().getSimpleName());
    }
}
