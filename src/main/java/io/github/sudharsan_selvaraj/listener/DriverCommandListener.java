package io.github.sudharsan_selvaraj.listener;

import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import org.openqa.selenium.WebDriver;


public interface DriverCommandListener<T extends WebDriver> {
    void beforeDriverCommandExecuted(DriverCommand<T> command);

    void afterDriverCommandExecuted(DriverCommandResult<T> command);

    void onException(DriverCommandException<T> command);
}
