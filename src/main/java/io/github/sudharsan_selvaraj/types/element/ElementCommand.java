package io.github.sudharsan_selvaraj.types.element;

import io.github.sudharsan_selvaraj.types.BaseCommand;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;


@Getter
public class ElementCommand<T extends WebDriver, U extends WebElement> extends BaseCommand {
    private T driver;
    private U element;
    private By locator;

    public ElementCommand(String id, T driver, U element, By locator, Method method, Object[] arguments) {
        super(id, method, arguments);
        this.driver = driver;
        this.element = element;
        this.locator = locator;
    }
}
