package io.github.sudharsan_selvaraj.interceptor;

import com.google.common.collect.Lists;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

public class ElementInterceptor extends BaseInterceptor {

    public ElementInterceptor(WebDriver driver, Object target, By locator, SpyDriverListener listener) {
        super(driver, target, locator, listener, Lists.newArrayList());
    }

    @Override
    protected Boolean skipListenerNotification(Method method, Object[] args) {
       return super.skipListenerNotification(method, args);
    }
}
