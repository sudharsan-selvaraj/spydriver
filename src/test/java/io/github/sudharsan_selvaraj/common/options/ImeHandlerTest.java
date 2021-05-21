package io.github.sudharsan_selvaraj.common.options;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ImeHandlerTest extends BaseWebDriverTest {

    @Test(description = "driver.manage().ime() should return proxied RemoteInputMethodManager object")
    public void imeTypeCastingTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.ImeHandler imeHandler = driver.manage().ime();

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "ime");
        assertTrue(imeHandler.getClass().getSimpleName().contains("RemoteInputMethodManager$MockitoMock"),
                "Actual Class Name:" + imeHandler.getClass().getSimpleName());
    }
}
