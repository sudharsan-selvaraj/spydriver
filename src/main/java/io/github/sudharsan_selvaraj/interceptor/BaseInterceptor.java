package io.github.sudharsan_selvaraj.interceptor;

import com.google.common.collect.Lists;
import io.github.sudharsan_selvaraj.MethodInvocationHandler;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseInterceptor implements Answer {

    protected SpyDriverListener listener;
    protected Object target;
    protected WebDriver driver;
    protected List<String> classesToBeProxied;

    protected MethodInvocationHandler handler;

    public BaseInterceptor(WebDriver driver,
                           Object target,
                           SpyDriverListener listener,
                           List<String> classesToBeProxied) {
        this.driver = driver;
        this.listener = listener;
        this.target = target;
        this.classesToBeProxied = classesToBeProxied;
        handler = new MethodInvocationHandler(driver, listener, target);
    }

    private Object[] processArgs(Method method, Object[] args) {
        if (method.isVarArgs()) {
            List<Object> newArgs = Lists.newArrayList();
            newArgs.addAll(Arrays.asList(args));
            newArgs.addAll(Collections.nCopies(method.getParameterCount() - args.length, new Object()));
            return newArgs.toArray();
        }
        return args;
    }

    abstract protected Boolean skipListenerNotification(Method method, Object[] args);


    public void notifyBefore(Object target, Method method, Object[] args) {
        if (isWebdriverInstance(target)) {
            listener.beforeDriverCommandExecuted(driver, target, method, args);
        } else {
            listener.beforeElementCommandExecuted(driver, (WebElement) target, method, args);
        }
    }

    public void notifyAfter(Object target, Method method, Object[] args, Object result) {
        if (isWebdriverInstance(target)) {
            listener.afterDriverCommandExecuted(driver, target, method, args, result);
        } else {
            listener.afterElementCommandExecuted(driver, (WebElement) target, method, args, result);
        }
    }

    public void notifyException(Object target, Method method, Object[] args, Throwable exception) {
        if (isWebdriverInstance(target)) {
            listener.onException(driver, target, method, args, exception);
        } else {
            listener.onException(driver, (WebElement) target, method, args, exception);
        }
    }

    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        method.setAccessible(true);

        Boolean skipNotification = skipListenerNotification(method, args);
        try {
            if (handler.isInternalMethod(method, args)) {
                return method.invoke(target, processArgs(method, args));
            }

            if (!skipNotification) {
                notifyBefore(target, method, args);
            }
            Object result = method.invoke(target, processArgs(method, args));
            if (result == null) {
                return null;
            }
            if (handler.isFindElementMethod(method, args)) {
                result = handler.processFindElement(method, args, result);
            } else if (classesToBeProxied.contains(result.getClass().getSimpleName())) {
                result = Mockito.mock(result.getClass(), new DriverInterceptor(driver, result, listener));
            }
            if (!skipNotification) {
                notifyAfter(target, method, args, result);
            }
            return result;
        } catch (Throwable e) {
            if (!skipNotification) {
                notifyException(target, method, args, e.getCause());
            }
            throw e.getCause();
        }

    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        return this.invoke(target, invocation.getMethod(), invocation.getArguments());
    }

    private boolean isWebdriverInstance(Object target) {
        return (target instanceof RemoteWebDriver) || target.getClass().getName().contains("RemoteWebDriver");
    }
}
