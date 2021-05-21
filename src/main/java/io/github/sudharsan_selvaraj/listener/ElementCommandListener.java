package io.github.sudharsan_selvaraj.listener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public interface ElementCommandListener<T extends WebDriver, U extends WebElement> {
    void beforeElementCommandExecuted(T driver, U element, Method method, Object[] args);

    void afterElementCommandExecuted(T driver, U element, Method method, Object[] args, Object result);

    void onException(T driver, U element, Method method, Object[] args, Throwable exception);
}
