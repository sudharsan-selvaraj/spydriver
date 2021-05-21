package io.github.sudharsan_selvaraj.listener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public interface DriverCommandListener<T extends WebDriver> {
    void beforeDriverCommandExecuted(T driver, Object target, Method method, Object[] args);

    void afterDriverCommandExecuted(T driver, Object target, Method method, Object[] args, Object result);

    void onException(T driver, Object target, Method method, Object[] args, Throwable exception);
}
