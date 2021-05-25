package io.github.sudharsan_selvaraj.listener;

import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;

public interface ElementCommandListener<T extends WebDriver, U extends WebElement> {
    void beforeElementCommandExecuted(ElementCommand<T, U> command);

    void afterElementCommandExecuted(ElementCommandResult<T, U> command);

    void onException(ElementCommandException<T, U> command);
}
