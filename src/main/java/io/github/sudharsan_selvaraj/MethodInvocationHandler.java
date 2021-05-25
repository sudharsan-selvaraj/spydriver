package io.github.sudharsan_selvaraj;

import io.github.sudharsan_selvaraj.interceptor.ElementInterceptor;
import lombok.AllArgsConstructor;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MethodInvocationHandler {

    private WebDriver driver;
    private SpyDriverListener listener;
    private Object target;

    private WebElement createProxiedElement(WebElement element, By locator) {
        return (WebElement) Mockito.mock(element.getClass(), new ElementInterceptor(driver, element, locator, listener));
    }

    private List<WebElement> createProxiedElement(List<WebElement> elementList, By locator) {
        return elementList
                .stream()
                .map(element -> this.createProxiedElement(element, locator))
                .collect(Collectors.toList());
    }

    public Boolean isFindElementMethod(Method method, Object[] args) {
        return method.getName().startsWith("findElement");
    }

    public Boolean isInternalMethod(Method method, Object[] args) {
        return method.getName().equals("toJson") || method.getName().equals("toString");
    }

    public Object processFindElement(Method method, Object[] args, Object result) throws Throwable {
        if (method.getName().startsWith("findElements")) {
            return createProxiedElement((List<WebElement>) result, (By) args[0]);
        } else if (method.getName().startsWith("findElement")) {
            return createProxiedElement((WebElement) result, (By) args[0]);
        }
        return result;
    }

}
