package examples;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sudharsan_selvaraj.SpyDriver;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import io.github.sudharsan_selvaraj.SpyDriverOptions;
import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class SpyDriverExample implements SpyDriverListener {

    public static void main(String[] args) {
        SpyDriverExample example = new SpyDriverExample();
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);

        SpyDriver spyDriver = new SpyDriver(new FirefoxDriver(), SpyDriverOptions.builder().listener(example).build());
        WebDriver driver = spyDriver.getSpyDriver();
        example.runTest(driver);
    }

    public void runTest(WebDriver driver) {
        driver.get("https://the-internet.herokuapp.com/");
        driver.manage().window().setSize(new Dimension(1000, 400));
        driver.findElement(By.partialLinkText("Inputs")).click();
        driver.findElement(By.cssSelector("[type=\"number\"]")).sendKeys("1222");

        driver.navigate().refresh();
        driver.navigate().back();

        driver.findElement(By.partialLinkText("Checkboxes")).click();

        WebElement checkbox = driver.findElement(By.cssSelector("#checkboxes input"));
        checkbox.isSelected();
        checkbox.click();
        checkbox.isSelected();

        driver.quit();
    }

    @Override
    public void beforeDriverCommandExecuted(DriverCommand command) {
        try {
            System.out.println("[WebDriver-before]" + command.getId());
            Thread.sleep(500);
        } catch (Exception e) {

        }
    }

    @Override
    public void afterDriverCommandExecuted(DriverCommandResult command) {
        System.out.println("[WebDriver-After]" + command.getId());
        if (command.getMethod().getName().startsWith("findElement")) {
            printFindElementCommand(command.getMethod(), command.getArguments());
            return;
        }
        switch (command.getMethod().getName()) {
            case "get":
                log("Navigated to:" + command.getArguments()[0]);
                break;
            case "quit":
                log("Quiting webdriver session");
                break;
            case "back":
                log("Navigating back");
                break;
            case "refresh":
                log("Refreshing current page");
                break;
            case "setSize":
                Dimension dimension = (Dimension) command.getArguments()[0];
                log("Setting browser size as width:" + dimension.width + " and height:" + dimension.height);
                break;
        }
    }

    @Override
    public void onException(DriverCommandException command) {
    }

    @Override
    public void beforeElementCommandExecuted(ElementCommand command) {
        System.out.println("[WebElement-Before]" + command.getId());
    }

    @Override
    public void afterElementCommandExecuted(ElementCommandResult command) {
        System.out.println("[WebElement-After]" + command.getId());
        switch (command.getMethod().getName()) {
            case "click" :
                log("Clicked on element " + command.getLocator());
                break;
            case "sendKeys":
                log("Entered " + Arrays.stream(((CharSequence[]) command.getArguments()[0])).collect(Collectors.joining(" ")) + " as value for element " + command.getLocator());
                break;
            case "isSelected":
                log("Checking isSelected on element " + command.getLocator() + ". Result: "+ command.getResult());
                break;
        }
    }

    @Override
    public void onException(ElementCommandException command) {

    }

    private void printFindElementCommand(Method method, Object[] arguments) {
        if (method.getName().toLowerCase().contains("by")) {
            log("Finding element by " + method.getName().split("By")[1] + ": " + arguments[0]);
        } else {
            log("Finding element by " + arguments[0].toString().split("\\.")[1]);
        }
    }

    public void log(String message) {
        System.out.println("[SpyDriver-LOG]: " + message);
    }
}
