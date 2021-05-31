package io.github.sudharsan_selvaraj;

import io.github.sudharsan_selvaraj.interceptor.DriverInterceptor;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

public class SpyDriver<T extends WebDriver> {

    private DriverEventDispatcher eventDispatcher;
    private T originalDriver, mockDriver;

    public SpyDriver(T driver) {
        this(driver, SpyDriverOptions.builder().build());
    }

    public SpyDriver(T driver, SpyDriverOptions options) {
        originalDriver = driver;
        eventDispatcher = getEventDispatcher(options);

        mockDriver = (T) Mockito.mock(getSpyInstanceClass(driver), new DriverInterceptor(driver, eventDispatcher));
    }

    private DriverEventDispatcher getEventDispatcher(SpyDriverOptions options) {
        DriverEventDispatcher eventDispatcher = new DriverEventDispatcher();
        if (options.getListener() != null) {
            eventDispatcher.addListener(options.getListener());
        }

        return eventDispatcher;
    }

    public void addListener(SpyDriverListener listener) {
        eventDispatcher.addListener(listener);
    }

    public T getWrappedDriver() {
        return originalDriver;
    }

    public T getSpyDriver() {
        return mockDriver;
    }

    private static <T extends WebDriver> Class<?> getSpyInstanceClass(T driver) {
        return driver.getClass();
    }

}
