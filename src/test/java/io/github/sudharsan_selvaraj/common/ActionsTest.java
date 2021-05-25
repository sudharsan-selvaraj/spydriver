package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.BaseWebDriverTest;
import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;;
import java.util.LinkedHashMap;

import static org.testng.Assert.*;

public class ActionsTest extends BaseWebDriverTest {

    @Test(description = "Test moveToElement method")
    public void moveToElementTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com/hovers");
        WebElement avatarImage = driver.findElement(By.cssSelector(".figure"));
        assertFalse(avatarImage.findElement(By.cssSelector(".figcaption")).isDisplayed());

        Actions actions = new Actions(driver);
        actions.moveToElement(avatarImage).perform();

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "perform");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments().length, 1);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), null);
        assertTrue(avatarImage.findElement(By.cssSelector(".figcaption")).isDisplayed());
    }

    @Test(description = "Test dragAndDrop method", enabled = false)
    public void dragAndDropTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement columnA = driver.findElement(By.cssSelector("#column-a"));
        WebElement columnB = driver.findElement(By.cssSelector("#column-b"));

        assertEquals(columnA.getText(), "A");
        assertEquals(columnB.getText(), "B");

        new Actions(driver).clickAndHold(columnA).moveToElement(columnB).release(columnA).perform();
        sleep(2000);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "perform");
        //assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0].length, 4);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), null);

        assertEquals(columnA.getText(), "B");
        assertEquals(columnB.getText(), "A");
    }

    @Test(description = "Test moveToElement and click method")
    public void moveToElementAndClickTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");

        WebElement input = driver.findElement(By.name("q"));

        new Actions(driver).moveToElement(input).click().sendKeys("TestNinja").perform();
        sleep(2000);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "perform");
        //assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0].length, 4);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), null);

        assertEquals(input.getAttribute("value"), "TestNinja");
    }

    @Test(description = "Test click and sendKeys method")
    public void clickAndSendKeysTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");

        WebElement input = driver.findElement(By.name("q"));

        new Actions(driver).click(input).sendKeys("TestNinja").perform();
        sleep(2000);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "perform");
        //assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0].length, 4);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), null);

        assertEquals(input.getAttribute("value"), "TestNinja");
    }

    @Test(description = "Test sendKeys method")
    public void sendKeysTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");

        WebElement input = driver.findElement(By.name("q"));

        new Actions(driver).sendKeys(input, "TestNinja").perform();
        sleep(2000);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "perform");
        //assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0].length, 4);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandResultStack).getResult(), null);

        assertEquals(input.getAttribute("value"), "TestNinja");
    }

}
