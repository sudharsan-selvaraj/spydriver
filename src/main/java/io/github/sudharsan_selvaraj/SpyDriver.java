package io.github.sudharsan_selvaraj;

import io.github.sudharsan_selvaraj.interceptor.DriverInterceptor;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

public class SpyDriver {

    public static <T extends WebDriver> T spyOn(T driver, SpryDriverOptions options) {
        return (T) Mockito.mock(getSpyInstanceClass(driver), new DriverInterceptor(driver, options.getListener()));
    }

    private static <T extends WebDriver> Class<?> getSpyInstanceClass(T driver) {
        return driver.getClass();
    }

}
