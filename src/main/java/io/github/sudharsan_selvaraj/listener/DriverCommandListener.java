package io.github.sudharsan_selvaraj.listener;

import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import org.openqa.selenium.WebDriver;


public interface DriverCommandListener<T extends WebDriver> {

    default void beforeDriverCommandExecuted(DriverCommand<T> command) {
    }

    default void afterDriverCommandExecuted(DriverCommandResult<T> command) {
    }

    default void onException(DriverCommandException<T> command) {
    }

}
