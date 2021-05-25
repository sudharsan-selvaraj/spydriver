package io.github.sudharsan_selvaraj.interceptor;

import com.google.common.collect.Lists;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.Logs;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DriverInterceptor extends BaseInterceptor implements Answer {

    private static List<String> classesToBeProxied;

    static {
        classesToBeProxied = Lists.newArrayList(
                "RemoteWebDriverOptions",
                "RemoteWindow",
                "RemoteTargetLocator",
                "RemoteTimeouts",
                "RemoteNavigation",
                "RemoteInputMethodManager",
                "RemoteAlert",
                "RemoteLogs"
        );
    }

    public DriverInterceptor(Object target, SpyDriverListener listener) {
        this((WebDriver) target, target, listener);
    }

    public DriverInterceptor(WebDriver driver, Object target, SpyDriverListener listener) {
        super(driver, target, listener, classesToBeProxied);
    }

    protected Boolean skipListenerNotification(Method method, Object[] args) {
        Throwable t = new Throwable();
        return Arrays.stream(t.getStackTrace())
                .filter(stackTraceElement -> {
                    return stackTraceElement.getClassName().contains("FluentWait");
                }).collect(Collectors.toList())
                .size() > 0;
    }
}
