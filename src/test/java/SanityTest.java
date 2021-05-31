import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sudharsan_selvaraj.SpyDriverOptions;
import io.github.sudharsan_selvaraj.SpyDriver;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class SanityTest implements SpyDriverListener {

    public static final String USERNAME = "";
    public static final String AUTOMATE_KEY = "";
    public static final String url = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public static void main(String[] args) throws MalformedURLException {
        WebDriverManager.chromedriver().setup();
        SanityTest test = new SanityTest();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setCapability("device", "Samsung Galaxy S10e");
        //WebDriver driver = spyDriver.spyOn(new AndroidDriver(new URL(url), capabilities));
        //WebDriver driver = spyDriver.spyOn(new AndroidDriver(new URL(url), capabilities));
        SpyDriver spyDriver = new SpyDriver(new ChromeDriver(), SpyDriverOptions.builder().listener(test).build());
        WebDriver driver = spyDriver.getSpyDriver();

        test.waitTest(driver);
        //spyDriver.addListener(test);
        //test.driverTest(driver);
        //test.elementTest(driver);
        driver.quit();
    }

    public void elementTest(WebDriver driver) {
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.cssSelector("body"));
        new Actions(driver).moveToElement(element).click().build().perform();
        element.getText();
        element.findElement(By.cssSelector("div")).getLocation();
        element.findElements(By.cssSelector("div")).get(0).findElement(By.cssSelector("div")).getText();
        element.click();
    }

    public void driverTest(WebDriver driver) {
        driver.get("http://www.google.com");
        driver.findElement(By.cssSelector("body"));
        driver.getTitle();
        String value = (String) ((JavascriptExecutor)driver).executeScript("return arguments[0].innerText", driver.findElement(By.cssSelector("body")));
//        driver.manage().window().maximize();
        driver.navigate().refresh();
        driver.getCurrentUrl();
        // ((ChromeDriver) driver).getLocalStorage().getItem("hi");
        //((ChromeDriver) driver).getWindowHandles();
        driver.quit();
    }

    public void waitTest(WebDriver driver) {
        driver.get("http://www.google.com");
        WebElement body = driver.findElement(By.cssSelector("body"));
        //new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#myname")));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(body));
    }

    @Override
    public void beforeDriverCommandExecuted(DriverCommand command) {
        System.out.println("Driver => " + command.getMethod().getDeclaringClass().getName() + " => " + command.getMethod().getName());
    }

    @Override
    public void afterDriverCommandExecuted(DriverCommandResult command) {

    }

    @Override
    public void onException(DriverCommandException command) {
        System.out.println("Driver Exception => ");
        command.getException().printStackTrace();
    }

    @Override
    public void beforeElementCommandExecuted(ElementCommand command) {
        System.out.println("Element => " + command.getMethod().getDeclaringClass().getName() + " => " + command.getMethod().getName());
    }

    @Override
    public void afterElementCommandExecuted(ElementCommandResult command) {

    }

    @Override
    public void onException(ElementCommandException command) {
        System.out.println("Element Exception => ");
        command.getException().printStackTrace();
    }
}
