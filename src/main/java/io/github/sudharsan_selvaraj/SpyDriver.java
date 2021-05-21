package io.github.sudharsan_selvaraj;

import com.google.common.collect.Lists;
import io.github.sudharsan_selvaraj.interceptor.DriverInterceptor;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.util.List;

public class SpyDriver implements SpyDriverListener {

    private final SpryDriverOptions options;
    private final List<SpyDriverListener> listeners = Lists.newArrayList();

    public SpyDriver(SpryDriverOptions options) {
        this.options = options;
        addListener(this.options.getListener());
    }

    public void addListener(SpyDriverListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public <T extends WebDriver> T spyOn(T driver) {
        return (T) Mockito.mock(getSpyInstanceClass(driver), new DriverInterceptor(driver, this));
    }

    private <T extends WebDriver> Class<?> getSpyInstanceClass(T driver) {
        return driver.getClass();
    }

    private List<SpyDriverListener> getListeners() {
        return listeners;
    }

    @Override
    public void beforeDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args) {
        getListeners()
                .forEach(listener -> listener.beforeDriverCommandExecuted(driver, target, method, args));
    }

    @Override
    public void afterDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args, Object result) {
        getListeners()
                .forEach(listener -> listener.afterDriverCommandExecuted(driver,target, method, args, result));
    }

    @Override
    public void onException(WebDriver driver, Object target, Method method, Object[] args, Throwable exception) {
        getListeners()
                .forEach(listener -> listener.onException(driver,target, method, args, exception));
    }

    @Override
    public void beforeElementCommandExecuted(WebDriver driver, WebElement element, Method method, Object[] args) {
        getListeners()
                .forEach(listener -> listener.beforeElementCommandExecuted(driver, element, method, args));
    }

    @Override
    public void afterElementCommandExecuted(WebDriver driver, WebElement element, Method method, Object[] args, Object result) {
        getListeners()
                .forEach(listener -> listener.afterElementCommandExecuted(driver, element, method, args, result));
    }

    @Override
    public void onException(WebDriver driver, WebElement element, Method method, Object[] args, Throwable exception) {
        getListeners()
                .forEach(listener -> listener.onException(driver, element, method, args, exception));
    }
}
