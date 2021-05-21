package io.github.sudharsan_selvaraj.common.options;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TimeoutsTest extends BaseWebDriverTest {

    @Test(description = "driver.manage().timeouts() should return proxied RemoteTimeouts object")
    public void timeoutsTypeCastingTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Timeouts timeouts = driver.manage().timeouts();

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "timeouts");
        assertTrue(timeouts.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"),
                "Actual Class Name:" + timeouts.getClass().getSimpleName());
    }

    @Test(description = "Test implicitlyWait method")
    public void implicitlyWaitTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        WebDriver.Timeouts timeoutsReturned = timeouts.implicitlyWait(1000, TimeUnit.MILLISECONDS);

        assertTrue(timeoutsReturned.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"));
        assertTrue(timeouts.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"),
                "Actual Class Name:" + timeouts.getClass().getSimpleName());

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "implicitlyWait");
        assertEquals((Long) mockListener.getLastInvocation().getArguments()[0], Long.valueOf(1000));
        assertEquals(mockListener.getLastInvocation().getArguments()[1], TimeUnit.MILLISECONDS);
    }

    @Test(description = "Test setScriptTimeout method")
    public void setScriptTimeoutTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        WebDriver.Timeouts timeoutsReturned = timeouts.setScriptTimeout(1000, TimeUnit.MILLISECONDS);

        assertTrue(timeoutsReturned.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"));
        assertTrue(timeouts.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"),
                "Actual Class Name:" + timeouts.getClass().getSimpleName());

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "setScriptTimeout");
        assertEquals((Long) mockListener.getLastInvocation().getArguments()[0], Long.valueOf(1000));
        assertEquals(mockListener.getLastInvocation().getArguments()[1], TimeUnit.MILLISECONDS);
    }

    @Test(description = "Test pageLoadTimeout method")
    public void pageLoadTimeoutTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Timeouts timeouts = driver.manage().timeouts();
        WebDriver.Timeouts timeoutsReturned = timeouts.pageLoadTimeout(1000, TimeUnit.MILLISECONDS);

        assertTrue(timeoutsReturned.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"));
        assertTrue(timeouts.getClass().getSimpleName().contains("RemoteTimeouts$MockitoMock"),
                "Actual Class Name:" + timeouts.getClass().getSimpleName());

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "pageLoadTimeout");
        assertEquals((Long) mockListener.getLastInvocation().getArguments()[0], Long.valueOf(1000));
        assertEquals(mockListener.getLastInvocation().getArguments()[1], TimeUnit.MILLISECONDS);
    }

}
