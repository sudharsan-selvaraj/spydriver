import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.sudharsan_selvaraj.SpryDriverOptions;
import io.github.sudharsan_selvaraj.SpyDriver;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        SpyDriver spyDriver = new SpyDriver(SpryDriverOptions.builder().listener(test).build());

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.ANDROID);
        capabilities.setCapability("device", "Samsung Galaxy S10e");
        //WebDriver driver = spyDriver.spyOn(new AndroidDriver(new URL(url), capabilities));
        WebDriver driver = spyDriver.spyOn(new AndroidDriver(new URL(url), capabilities));
        //WebDriver driver = spyDriver.spyOn(new ChromeDriver());

        //test.waitTest(driver);
        //spyDriver.addListener(test);
        test.elementTest(driver);
        test.driverTest(driver);
        driver.quit();
    }

    public void elementTest(WebDriver driver) {
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.cssSelector("body"));
        element.getText();
        new Actions(driver).moveToElement(element).click().build().perform();
        element.findElement(By.cssSelector("div")).getLocation();
        element.findElements(By.cssSelector("div")).get(0).findElement(By.cssSelector("div")).getText();
        element.click();
    }

    public void driverTest(WebDriver driver) {
        driver.get("http://www.google.com");
        driver.findElement(By.cssSelector("body"));
        driver.getTitle();
//        driver.manage().window().maximize();
        driver.navigate().refresh();
        driver.getCurrentUrl();
        // ((ChromeDriver) driver).getLocalStorage().getItem("hi");
        //((ChromeDriver) driver).getWindowHandles();
        driver.quit();
    }

    public void waitTest(WebDriver driver) {
        driver.get("http://www.google.com");
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#myname")));
    }

    @Override
    public void beforeDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args) {
        System.out.println(method.getDeclaringClass().getName() + " => " + method.getName());
    }

    @Override
    public void afterDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args, Object result) {

    }

    @Override
    public void onException(WebDriver driver, Object target, Method method, Object[] args, Throwable exception) {
        System.out.println("Webdriver exception:");
        exception.printStackTrace();
    }

    @Override
    public void beforeElementCommandExecuted(WebDriver driver, WebElement element, Method method, Object[] args) {
        System.out.println(method.getDeclaringClass().getName() + " => " + method.getName());
    }

    @Override
    public void afterElementCommandExecuted(WebDriver driver, WebElement element, Method method, Object[] args, Object result) {

    }

    @Override
    public void onException(WebDriver driver, WebElement element, Method method, Object[] args, Throwable exception) {
        System.out.println("Element exception:");
        exception.printStackTrace();
    }
}
