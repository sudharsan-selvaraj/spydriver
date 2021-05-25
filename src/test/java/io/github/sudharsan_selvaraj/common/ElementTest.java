package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ElementTest extends BaseWebDriverTest {


    @Test(description = "Test click method")
    public void clickTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com");
        WebElement inputLink = driver.findElement(By.linkText("Inputs"));

        inputLink.click();

        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "click");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertNull(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult());
        assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/inputs");

    }

    @Test(description = "Test sendKeys method")
    public void sendKeysTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));

        input.sendKeys("Test", " ", "Ninja");

        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "sendKeys");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments()[0], new CharSequence[]{"Test", " ", "Ninja"});
        assertNull(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult());
        assertEquals(input.getAttribute("value"), "Test Ninja");

    }

    @Test(description = "Test clear method")
    public void clearTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));

        input.sendKeys("Test", " ", "Ninja");
        assertEquals(input.getAttribute("value"), "Test Ninja");

        input.clear();
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "clear");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertNull(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult());
    }

    @Test(description = "Test getTagName method")
    public void getTagNameTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));

        assertEquals(input.getTagName(), "input");

        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "getTagName");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), "input");
    }

    @Test(description = "Test getAttribute method")
    public void getAttributeTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));

        assertEquals(input.getAttribute("name"), "q");

        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "getAttribute");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{"name"});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), "q");
    }

    @Test(description = "Test isSelected method")
    public void isSelectedTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkBox = driver.findElement(By.tagName("input"));

        assertFalse(checkBox.isSelected());
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "isSelected");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), false);

        checkBox.click();

        assertTrue(checkBox.isSelected());
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "isSelected");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), true);
    }

    @Test(description = "Test isEnabled method")
    public void isEnabledTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement button = driver.findElement(By.name("btnK"));

        assertTrue(button.isEnabled());
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "isEnabled");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), true);
    }

    @Test(description = "Test getText method")
    public void getTextTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com/");
        WebElement heading = driver.findElement(By.cssSelector("h1.heading"));

        assertEquals(heading.getText(), "Welcome to the-internet");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getMethod().getName(), "getText");
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandStack).getArguments(), new Object[]{});
        assertEquals(mockListener.getLastInvocation(mockListener.elementCommandResults).getResult(), "Welcome to the-internet");
    }

}
