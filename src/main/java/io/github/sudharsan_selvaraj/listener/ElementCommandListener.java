package io.github.sudharsan_selvaraj.listener;

import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public interface ElementCommandListener<T extends WebDriver, U extends WebElement> {

    default void beforeElementCommandExecuted(ElementCommand<T, U> command) {
    }

    default void afterElementCommandExecuted(ElementCommandResult<T, U> command) {
    }

    default void onException(ElementCommandException<T, U> command) {
    }

}
