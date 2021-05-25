package io.github.sudharsan_selvaraj.types.element;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

@Getter
public class ElementCommandResult<T extends WebDriver, U extends WebElement> extends ElementCommand<T, U> {
    private Object result;

    public ElementCommandResult(T driver, U element, By locator, Method method, Object[] arguments, Object result) {
        super(driver, element, locator, method, arguments);
        this.result = result;
    }
}
