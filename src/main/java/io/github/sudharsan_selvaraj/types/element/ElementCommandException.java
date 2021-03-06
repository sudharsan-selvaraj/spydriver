package io.github.sudharsan_selvaraj.types.element;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

@Getter
public class ElementCommandException<T extends WebDriver, U extends WebElement> extends ElementCommand<T, U> {
    private Throwable exception;

    public ElementCommandException(String id,T driver, U element, By locator, Method method, Object[] arguments, Throwable exception) {
        super(id, driver, element, locator, method, arguments);
        this.exception = exception;
    }
}
