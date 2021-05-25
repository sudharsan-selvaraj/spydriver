package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AlertTest extends BaseWebDriverTest {


    private void openAlert(WebDriver driver) {
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        sleep(2000);

        driver.findElements(By.cssSelector("button")).get(0).click();
    }

    @Test(description = "Test driver.switchTo().alert() returns mocked alert object")
    public void testAlertObjectMocked() {
        WebDriver driver = localDriver.get();
        openAlert(driver);
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        Alert alert = driver.switchTo().alert();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "alert");
        assertTrue(alert.getClass().getSimpleName().contains("RemoteAlert$MockitoMock"));
    }

    @Test(description = "Test alert accept methods")
    public void accept() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        openAlert(driver);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "I am a JS Alert");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getText");

        alert.accept();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "accept");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test alert dismiss methods")
    public void dismiss() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        openAlert(driver);
        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "I am a JS Alert");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getText");

        alert.dismiss();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "dismiss");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

}
