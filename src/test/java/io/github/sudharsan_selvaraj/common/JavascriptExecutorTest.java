package io.github.sudharsan_selvaraj.common;

import io.github.sudharsan_selvaraj.MockDriverListener;
import org.openqa.selenium.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class JavascriptExecutorTest extends WebDriverTest {

    @Test(description = "Test executeScript method without passing any variable arguments")
    public void executeScriptEmptyParameterTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        ((JavascriptExecutor) driver).executeScript("alert('TestNinja')");

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], "alert('TestNinja')");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{});

        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "TestNinja");

    }

    @Test(description = "Test executeScript method by passing a string argument")
    public void executeScriptWithStringParameterTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        ((JavascriptExecutor) driver).executeScript("alert(arguments[0])", "TestNinja");

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], "alert(arguments[0])");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{"TestNinja"});

        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "TestNinja");

    }

    @Test(description = "Test executeScript method by passing a webelement argument")
    public void executeScriptWithElementParameterTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));
        input.sendKeys("TestNinja");
        ((JavascriptExecutor) driver).executeScript("alert(arguments[0].value)", input);

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], "alert(arguments[0].value)");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{input});

        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "TestNinja");

    }

    @Test(description = "Test executeScript method by passing a multiple webelement argument")
    public void executeScriptWithMultipleParameterTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));
        input.sendKeys("TestNinja");
        String script = "alert(arguments[0].value + ' '+ arguments[1].value + ' '+ arguments[2])";
        ((JavascriptExecutor) driver)
                .executeScript(script, input, input, "TestNinja");

        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], script);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{input, input, "TestNinja"});

        Alert alert = driver.switchTo().alert();
        assertEquals(alert.getText(), "TestNinja TestNinja TestNinja");

    }

    @Test(description = "Test executeScript methods to return a string value")
    public void executeScriptWithStringReturnTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));
        input.sendKeys("TestNinja");
        String script = "return arguments[0].value";
        String returnValue = (String) ((JavascriptExecutor) driver)
                .executeScript(script, input);

        assertEquals(returnValue, "TestNinja");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], script);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{input});
    }

    @Test(description = "Test executeScript methods to return a WebElement value")
    public void executeScriptWithElementReturnTest() {
        WebDriver driver = localDriver.get();
        MockDriverListener mockListener = (MockDriverListener) listener.get();

        driver.get("https://www.google.com");
        WebElement input = driver.findElement(By.name("q"));
        input.sendKeys("TestNinja");
        String script = "return document.querySelector(arguments[0])";
        WebElement returnValue = (WebElement) ((JavascriptExecutor) driver)
                .executeScript(script, "[name='q']");

        assertEquals(returnValue.getAttribute("value"), "TestNinja");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getMethod().getName(), "executeScript");
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[0], script);
        assertEquals(mockListener.getLastInvocation(mockListener.driverCommandStack).getArguments()[1], new Object[]{"[name='q']"});
    }

}
