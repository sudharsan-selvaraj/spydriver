package io.github.sudharsan_selvaraj.interceptor;

import com.google.common.collect.Lists;
import io.github.sudharsan_selvaraj.MethodInvocationHandler;
import io.github.sudharsan_selvaraj.SpyDriverListener;
import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

public abstract class BaseInterceptor implements Answer {

    protected SpyDriverListener listener;
    protected Object target;
    protected WebDriver driver;
    protected By locator;
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

    public BaseInterceptor(WebDriver driver,
                           Object target,
                           By locator,
                           SpyDriverListener listener,
                           List<String> classesToBeProxied) {
        this.driver = driver;
        this.listener = listener;
        this.target = target;
        this.classesToBeProxied = classesToBeProxied;
        this.locator = locator;
        handler = new MethodInvocationHandler(driver, listener, target);
    }

    private Object[] processArgs(Method method, Object[] args) {
        if (method.isVarArgs()) {
            if (method.getParameterCount() > 1) {
                List<Object> newArgs = Lists.newArrayList();
                newArgs.addAll(Arrays.asList(Arrays.copyOfRange(args, 0, method.getParameterCount() - 1)));
                if (args.length < method.getParameterCount()) {
                    newArgs.add(new Object[]{});
                } else {
                    newArgs.add(Arrays.copyOfRange(args, method.getParameterCount() - 1, args.length));
                }
                return newArgs.toArray();
            } else {
                Class<?> parameterClass = method.getParameters()[method.getParameterCount() - 1].getType().getComponentType();
                return typecastVarArguments(parameterClass, args);
            }
        }
        return args;
    }

    abstract protected Boolean skipListenerNotification(Method method, Object[] args);


    public void notifyBefore(Object target, Method method, Object[] args) {
        if (isWebdriverInstance(target)) {
            listener.beforeDriverCommandExecuted(constructDriverCommand(method, args));
        } else {
            listener.beforeElementCommandExecuted(constructElementCommand(method, args));
        }
    }

    public void notifyAfter(Object target, Method method, Object[] args, Object result) {
        if (isWebdriverInstance(target)) {
            listener.afterDriverCommandExecuted(constructDriverCommandResult(method, args, result));
        } else {
            listener.afterElementCommandExecuted(constructElementCommandResult(method, args, result));
        }
    }

    public void notifyException(Object target, Method method, Object[] args, Throwable exception) {
        if (isWebdriverInstance(target)) {
            listener.onException(constructDriverCommandException(method, args, exception));
        } else {
            listener.onException(constructElementCommandException(method, args, exception));
        }
    }

    public Object invoke(Object target, Method method, Object[] rawArgs) throws Throwable {
        method.setAccessible(true);
        Object[] parsedArgs = processArgs(method, rawArgs);
        Boolean skipNotification = skipListenerNotification(method, parsedArgs);
        try {
            if (handler.isInternalMethod(method, parsedArgs)) {
                return method.invoke(target, parsedArgs);
            }

            if (!skipNotification) {
                notifyBefore(target, method, parsedArgs);
            }
            Object result = method.invoke(target, parsedArgs);
            if (result != null) {
                if (handler.isFindElementMethod(method, parsedArgs)) {
                    result = handler.processFindElement(method, parsedArgs, result);
                } else if (classesToBeProxied.contains(result.getClass().getSimpleName())) {
                    result = Mockito.mock(result.getClass(), new DriverInterceptor(driver, result, listener));
                }
            }
            if (!skipNotification) {
                notifyAfter(target, method, parsedArgs, result);
            }
            return result;
        } catch (Throwable e) {
            if (!skipNotification) {
                notifyException(target, method, parsedArgs, e.getCause());
            }
            if (e.getCause() != null) {
                throw e.getCause();
            }
            throw e;
        }

    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        return this.invoke(target, invocation.getMethod(), invocation.getArguments());
    }

    private boolean isWebdriverInstance(Object target) {
        return (target instanceof RemoteWebDriver) ||
                target.getClass().getName().contains("RemoteWebDriver") ||
                target.getClass().getSimpleName().equals("RemoteLogs");
    }

    private <T> Object[] typecastVarArguments(Class<T> tClass, Object[] args) {
        T[] castedArray = (T[]) Array.newInstance(tClass, args.length);
        for (int i = 0; i < args.length; i++) {
            castedArray[i] = tClass.cast(args[i]);
        }
        return new Object[]{castedArray};
    }

    private DriverCommand constructDriverCommand(Method method, Object[] args) {
        return new DriverCommand(driver, target, method, args);
    }

    private ElementCommand constructElementCommand(Method method, Object[] args) {
        return new ElementCommand(driver, (WebElement) target, locator, method, args);
    }

    private DriverCommandResult constructDriverCommandResult(Method method, Object[] args, Object result) {
        return new DriverCommandResult(driver, target, method, args, result);
    }

    private ElementCommandResult constructElementCommandResult(Method method, Object[] args, Object result) {
        return new ElementCommandResult(driver, (WebElement) target, locator, method, args, result);
    }

    private DriverCommandException constructDriverCommandException(Method method, Object[] args, Throwable exception) {
        return new DriverCommandException(driver, target, method, args, exception);
    }

    private ElementCommandException constructElementCommandException(Method method, Object[] args, Throwable exception) {
        return new ElementCommandException(driver, (WebElement) target, locator, method, args, exception);
    }
}
