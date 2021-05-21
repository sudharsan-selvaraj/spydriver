package io.github.sudharsan_selvaraj.common.options;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WindowTest extends BaseWebDriverTest {

    @Test(description = "driver.manage().window() should return proxied RemoteWindow object")
    public void windowTypeCastingTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Window window = driver.manage().window();

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "window");
        assertTrue(window.getClass().getSimpleName().contains("RemoteWindow$MockitoMock"),
                "Actual Class Name:" + window.getClass().getSimpleName());
    }

    @Test(description = "Test setSize method")
    public void setSizeTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Window window = driver.manage().window();
        Dimension dimension = new Dimension(1000, 700);
        window.setSize(dimension);

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "setSize");
        assertEquals(mockListener.getLastInvocation().getArguments()[0], dimension);

        assertEquals(driver.manage().window().getSize(), dimension);
    }


    @Test(description = "Test setPosition method")
    public void setPositionTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Window window = driver.manage().window();
        Point point = new Point(10, 40);
        window.setPosition(point);

        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "setPosition");
        assertEquals(mockListener.getLastInvocation().getArguments()[0], point);

        assertEquals(driver.manage().window().getPosition(), point);
    }

    @Test(description = "Test maximize method")
    public void maximizeTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Window window = driver.manage().window();
        Dimension dimension = new Dimension(1000, 700);
        window.setSize(dimension);

        assertEquals(driver.manage().window().getSize(), dimension);

        window.maximize();
        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "maximize");
        assertEquals(mockListener.getLastInvocation().getArguments().length, 0);
        assertNotEquals(driver.manage().window().getSize(), dimension);
    }

    @Test(description = "Test fullscreen method")
    public void fullscreenTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Window window = driver.manage().window();
        Dimension dimension = new Dimension(1000, 700);
        window.setSize(dimension);

        assertEquals(driver.manage().window().getSize(), dimension);

        window.fullscreen();
        assertEquals(mockListener.getLastInvocation().getMethod().getName(), "fullscreen");
        assertEquals(mockListener.getLastInvocation().getArguments().length, 0);
        assertNotEquals(driver.manage().window().getSize(), dimension);
    }

}
