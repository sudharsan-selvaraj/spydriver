package io.github.sudharsan_selvaraj.types.driver;

import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

@Getter
public class DriverCommandException<T extends WebDriver> extends DriverCommand<T> {
    private Throwable exception;

    public DriverCommandException(String id,T driver, Object target, Method method, Object[] arguments, Throwable exception) {
        super(id, driver, target, method, arguments);
        this.exception = exception;
    }

}
