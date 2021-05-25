package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class CastedRemoteWebDriverTest extends BaseWebDriverTest {

//    @Test
//    public void setFileDetectorTest() {
//        RemoteWebDriver driver = (RemoteWebDriver) localDriver.get();
//        MockDriverListener mockListener = (MockDriverListener) listener.get();
//
//        /* Test Get Method */
//        driver.get("https://the-internet.herokuapp.com/inputs");
//
//        FileDetector detector = new LocalFileDetector();
//        driver.setFileDetector(detector);
//
//        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "setFileDetector");
//        assertEquals(mockListener.getLastInvocation().getArguments()[0], detector);
//    }

    @Test(description = "Test getSessionId test")
    public void getSessionIdTest() {
        RemoteWebDriver driver = (RemoteWebDriver) localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com/inputs");

        assertNotEquals(driver.getSessionId().toString().length(), 0);
    }

    @Test(description = "Test getCapabilitiesTest method")
    @Parameters({"browser"})
    public void getCapabilitiesTest(String browser) {
        RemoteWebDriver driver = (RemoteWebDriver) localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        Capabilities capabilities = driver.getCapabilities();

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getCapabilities");
        assertNotEquals(capabilities.getBrowserName().length(), 0);
    }

    @Test(description = "Test getScreenShotAs method")
    public void getScreenShotAs() {
        RemoteWebDriver driver = (RemoteWebDriver) localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        String base64 = driver.getScreenshotAs(OutputType.BASE64);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getScreenshotAs");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], OutputType.BASE64);
        assertNotEquals(base64.length(), 0);
    }

}
