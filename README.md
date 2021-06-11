<p align="center">
<img src="./assets/icon.svg" height=150/>
</p>

<p align="center">
   <i><strong>Lightweight utility to intercept webdriver and webelement method calls. Supports both Selenium and Appium drivers </strong></i>
<p>
 
### <p align="center"> [About](#about) **|** [To Get Started](#how) **|** [Installation](#installation) </p>

---

## About

Spydriver is a utility library which helps in intercepting all webdriver and webelement method calls and enables us to
perform any operation before and after the method is executed.
<p>
  <img src="./assets/recording_qtp.gif" alt="spydriver.gif"/>
</p>

## How?:

### Creating the listener object:

```java
 SpyDriverListener listener = new SpyDriverListener() {

        @Override
        public void beforeDriverCommandExecuted(DriverCommand command) {
            System.out.println("[webdriver] Before " + command.getMethod().getName() +" => id: "+ command.getId());
            //Perform any action
        }
        
        @Override
        public void afterDriverCommandExecuted(DriverCommandResult command) {
            System.out.println("[webdriver] After " + command.getMethod().getName() +" => id: "+ command.getId());
            //Perform any action
        }
        
        @Override
        public void onException(DriverCommandException command) {
             //Perform any action
        }
        
        @Override
        public void beforeElementCommandExecuted(ElementCommand command) {
            System.out.println("[webelement] Before " + command.getMethod().getName() +" => id: "+ command.getId());
            //Perform any action
        }
        
        @Override
        public void afterElementCommandExecuted(ElementCommandResult command) {
            System.out.println("[webelement] After " + command.getMethod().getName() +" => id: "+ command.getId());
            //Perform any action
        }
        
        @Override
        public void onException(ElementCommandException command) {
            //Perform any action
        }
    }
```

### Creating the spy driver object:

```java
SpyDriver spyDriver = new SpyDriver(new FirefoxDriver(), //Any webdriver object(supports both Selenium and Appium)
        SpyDriverOptions.builder().listener(listener).build());// Listener that we have created above
WebDriver driver = spyDriver.getSpyDriver();
//now the driver object can be used in the test to get the events via listeners
```
It's also possible to add any number of listeners later using
```java
spyDriver.addListener(listener);
```


That's it. Now you can use the driver object in your test and it will log each and every method name that is invoked in the driver object. 

### Example:

```java
driver.get("https://the-internet.herokuapp.com/");
driver.manage().window().setSize(new Dimension(1000, 400));
driver.findElement(By.partialLinkText("Inputs")).click();
driver.findElement(By.cssSelector("[type=\"number\"]")).sendKeys("1222");
driver.quit();
```

### Output:
```shell
[webdriver] Before get => id: a1275da9-f10b-4ab6-a268-3b963b83dc4a
[webdriver] After get => id: a1275da9-f10b-4ab6-a268-3b963b83dc4a
[webdriver] Before setSize => id: 4aff25ac-8c30-496b-a299-76a4bd1b0dbe
[webdriver] After setSize => id: 4aff25ac-8c30-496b-a299-76a4bd1b0dbe
[webdriver] Before findElement => id: 9d7e9167-6f5b-4f13-9f29-66c2e0239889
[webdriver] After findElement => id: 9d7e9167-6f5b-4f13-9f29-66c2e0239889
[webelement] Before click => id: 57247a4e-5be9-4af3-b9fa-c9142bacce67
[webelement] After click => id: 57247a4e-5be9-4af3-b9fa-c9142bacce67
[webdriver] Before findElement => id: 8d57434c-6c02-4e41-934f-86771fce9226
[webdriver] After findElement => id: 8d57434c-6c02-4e41-934f-86771fce9226
[webelement] Before sendKeys => id: 0233e88f-6e89-47a0-aa60-a3f3b94319c9
[webelement] After sendKeys => id: 0233e88f-6e89-47a0-aa60-a3f3b94319c9
[webdriver] Before quit => id: aa31341e-c464-4789-98f2-bb0aaa5cec87
[webdriver] After quit => id: aa31341e-c464-4789-98f2-bb0aaa5cec87
```

Check [SpyDriverExample.java](/src/test/java/examples/SpyDriverExample.java) for more usage.

## Hooks:

### beforeDriverCommandExecuted:
Invoked before the webdriver method is called.
Parameter: `DriverCommand`
```java
driver.get("https://www.google.com");

public void beforeDriverCommandExecuted(DriverCommand command) {
    System.out.println(command.getMethod().getName()); // prints "get"
    System.out.println(command.getArguments()[0]); // prints "https://www.google.com"
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
    
    Thread.sleep(2000); //Wait 2s before calling the get method
    //You can add any cutom logic here in blocking mode   
}
```

### afterDriverCommandExecuted:
Invoked after the webdriver method is called. It also holds the return value from the original method call.

Parameter: `DriverCommandResult`
```java
//sample driver method
driver.getTitle();

public void afterDriverCommandExecuted(DriverCommandResult command) {
    System.out.println(command.getMethod().getName()); // prints "getTitle"
    System.out.println(command.getArguments()); // prints "[]" (because there are no parameters)
    System.out.println(command.getResult()); // prints "Google"
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
}
```

```java
//sample driver method
driver.findElement(By.cssSelector("body"));

public void afterDriverCommandExecuted(DriverCommandResult command) {
    System.out.println(command.getMethod().getName()); // prints "findElement"
    By locator = (By) command.getArguments()[0];
    WebElement locatedElement = (WebElement) command.getResult();
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
}
```

```java
//sample driver method
driver.manage().window().getSize();

public void afterDriverCommandExecuted(DriverCommandResult command) {
    System.out.println(command.getMethod().getName()); // prints "getSize"
    System.out.println(command.getArguments()); // prints "[]" (because there are no parameters)
    Dimension size = (Dimension) command.getResult(); // returns the current window size
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
}
```

### onException:
Invoked if any exception is thrown while calling the webdriver method.

Parameter: `DriverCommandException`
```java
//sample driver method
driver.get("some-invalid-url");

public void onException(DriverCommandException command) {
    System.out.println(command.getMethod().get()); // prints "get"
    System.out.println(command.getArguments()[0]); // prints "some-invalid-url"
    System.out.println(command.getException().getMessage()); // Malformed URL: URL constructor: some-invalid-url is not a valid URL.
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
}
```

### beforeElementCommandExecuted:
Invoked before any method is called in WebElement object.

Parameter: `ElementCommand`

```java
//sample driver method
WebElement element = driver.findElement(By.cssSelector(".username"));
element.sendKeys("Test", " ", "Ninja");

public void beforeElementCommandExecuted(ElementCommand command) {
    System.out.println(command.getMethod().getName()); // prints "sendKeys"
    System.out.println(command.getArguments()[0]); // prints ["Test", " ", "Ninja"]
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
    Webdriver originalElement = command.getElement(); // return the original element object that is being spied.
    By locator = command.getLocator(); // returns the locator used to find the original element (By.cssSelector(".username")).
}
```

```java
//sample driver method
WebElement element = driver.findElement(By.cssSelector(".username"));
element.click();

public void beforeElementCommandExecuted(ElementCommand command) {
    System.out.println(command.getMethod().getName()); // prints "click"
    System.out.println(command.getArguments()); // prints "[]"
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
    Webdriver originalElement = command.getElement(); // return the original element object that is being spied.
    By locator = command.getLocator(); // returns the locator used to find the original element (By.cssSelector(".username")).
}
```

### afterElementCommandExecuted:
Invoked after any method is called in WebElement object.

Parameter: `ElementCommandResult`
```java
//sample driver method
WebElement element = driver.findElement(By.cssSelector(".username"));
String username = element.getAttribute("value");

public void afterElementCommandExecuted(ElementCommandResult command) {
    System.out.println(command.getMethod().getName()); // prints "getAttribute"
    System.out.println(command.getArguments()[0]); // prints ["value"]
    System.out.println(command.getResult()); // prints "Test Ninaje"
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
    Webdriver originalElement = command.getElement(); // return the original element object that is being spied.
    By locator = command.getLocator(); // returns the locator used to find the original element (By.cssSelector(".username"))
}
```

### onException:
Invoked if any exception is thrown while calling the webelement method.

Parameter: `ElementCommandException`
```java
//sample driver method
WebElement element = driver.findElement(By.cssSelector(".hidden"));
element.click();

public void onException(ElementCommandException command) {
    System.out.println(command.getMethod().getName()); // prints "click"
    System.out.println(command.getException().getMessage()); // prints "element not visile"
    Webdriver originalDriver = command.getDriver(); // return the original driver object that is being spied.
    Webdriver originalElement = command.getElement(); // return the original element object that is being spied.
    By locator = command.getLocator(); // returns the locator used to find the original element (By.cssSelector(".hidden"))
}
```

## Installation:

### Maven:

```xml
<dependency>
  <groupId>io.github.sudharsan-selvaraj</groupId>
  <artifactId>spydriver</artifactId>
  <version>1.1.1</version>
</dependency> 
```

### Gradle:

```groovy
implementation group: 'io.github.sudharsan-selvaraj', name: 'spydriver', version: '1.1.1'
```