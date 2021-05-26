package io.github.sudharsan_selvaraj.types.driver;

import io.github.sudharsan_selvaraj.types.BaseCommand;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

@Getter
public class DriverCommand<T extends WebDriver> extends BaseCommand {
    private final T driver;
    private final Object target;

    public DriverCommand(String id, T driver, Object target, Method method, Object[] arguments) {
        super(id, method, arguments);
        this.driver = driver;
        this.target = target;
    }
}
