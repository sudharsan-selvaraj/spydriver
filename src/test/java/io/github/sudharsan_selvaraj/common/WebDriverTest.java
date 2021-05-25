package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.MockDriverListener;
import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

import static org.testng.Assert.*;

public class WebDriverTest extends BaseWebDriverTest {

    @Test(description = "Test get method")
    public void get() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/inputs");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "get");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{"https://the-internet.herokuapp.com/inputs"});
        assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/inputs");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test getTitle method")
    public void getTitle() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://www.google.com");
        assertEquals(driver.getTitle(), "Google");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getTitle");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{});

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test getPageSource method")
    public void getPageSource() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/inputs");
        assertTrue(driver.getPageSource().contains("<title>The Internet</title>"));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getPageSource");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{});

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test getWindowHandles method")
    public void getWindowHandles() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/inputs");
        assertEquals(driver.getWindowHandles().size(), 1);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getWindowHandles");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{});

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test getWindowHandle method")
    public void getWindowHandle() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/inputs");
        assertNotNull(driver.getWindowHandle());
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "getWindowHandle");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{});

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test switchTo method")
    public void switchTo() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/iframe");
        sleep(2000);
        WebDriver.TargetLocator locator = driver.switchTo();
        assertNotNull(locator);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "switchTo");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments(), new Object[]{});

        locator.frame(0);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "frame");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], 0);

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test switchTo.frame by index method")
    public void switchToFrameIndex() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/iframe");
        sleep(2000);

        driver.switchTo().frame(0);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "frame");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], 0);
        assertEquals(driver.findElement(By.cssSelector("body")).getAttribute("id"), "tinymce");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test switchTo.frame by id method")
    public void switchToFrameId() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/iframe");
        sleep(2000);

        driver.switchTo().frame("mce_0_ifr");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "frame");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], "mce_0_ifr");
        assertEquals(driver.findElement(By.cssSelector("body")).getAttribute("id"), "tinymce");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }


    @Test(description = "Test switchTo.window method")
    public void switchToWindow() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();
        /* Test Get Method */
        driver.get("https://the-internet.herokuapp.com/windows");
        sleep(2000);

        driver.findElement(By.linkText("Click Here")).click();
        sleep(2000);
        List<String> windowHandles = Lists.newArrayList(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(1));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "window");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], windowHandles.get(1));
        assertEquals(driver.getTitle(), "New Window");

        assertEquals(driver.getWindowHandle(), mockListener.getLastInvocation(mockListener.driverCommandStack).getDriver().getWindowHandle());
    }

    @Test(description = "Test findElement method")
    public void findElementTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));

        assertTrue(input.getClass().getSimpleName().contains("RemoteWebElement$MockitoMock"), "Actual class:" + input.getClass().getSimpleName());
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "findElement");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], By.name("q"));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), input);
    }

    @Test(description = "Test findElements method")
    public void findElementsTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        List<WebElement> input = driver.findElements(By.name("q"));

        assertTrue(input.get(0).getClass().getSimpleName().contains("RemoteWebElement$MockitoMock"), "Actual class:" + input.getClass().getSimpleName());
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "findElements");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], By.name("q"));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), input);
    }

}
