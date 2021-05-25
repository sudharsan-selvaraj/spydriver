package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NavigationTest extends BaseWebDriverTest {

    @Test(description = "Test driver.navigate() returns mocked RemoteNavigation object")
    public void testNavigationObjectMocked() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Navigation navigation = driver.navigate();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "navigate");
        assertTrue(navigation.getClass().getSimpleName().contains("RemoteNavigation$MockitoMock"));
    }

    @Test(description = "Test to() Method")
    public void toTest() throws MalformedURLException {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebDriver.Navigation navigation = driver.navigate();

        navigation.to("https://www.youtube.com");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "to");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], "https://www.youtube.com");
        assertTrue(driver.getTitle().contains("YouTube"));

        navigation.to(new URL("https://www.google.com"));
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "to");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], new URL("https://www.google.com"));
        assertTrue(driver.getTitle().contains("Google"));
    }

    @Test(description = "Test to() Method")
    public void refreshTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement searchInput = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[name=\"q\"]")));
        searchInput.sendKeys("Test Ninja" , "");
        WebDriver.Navigation navigation = driver.navigate();

        navigation.refresh();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "refresh");
        assertEquals(driver.findElement(By.cssSelector("[name=\"q\"]")).getAttribute("value"), "");
    }

    @Test(description = "Test back() Method")
    public void backTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        driver.get("https://www.youtube.com");
        assertTrue(driver.getTitle().contains( "YouTube"));

        WebDriver.Navigation navigation = driver.navigate();
        navigation.back();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "back");
        assertEquals(driver.getTitle(), "Google");
    }

    @Test(description = "Test forward() Method")
    public void forwardTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        driver.get("https://www.youtube.com");
        assertTrue(driver.getTitle().contains( "YouTube"));

        WebDriver.Navigation navigation = driver.navigate();
        navigation.back();
        assertTrue(driver.getTitle().contains( "Google"));
        navigation.forward();
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "forward");
        assertTrue(driver.getTitle().contains( "YouTube"));
    }
}
