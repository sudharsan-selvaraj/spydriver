package io.github.sudharsan_selvaraj.types.driver;

import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

@Getter
public class DriverCommandResult<T extends WebDriver> extends DriverCommand<T> {
    private Object result;

    public DriverCommandResult(String id,T driver, Object target, Method method, Object[] arguments, Object result) {
        super(id, driver, target, method, arguments);
        this.result = result;
    }
}
