package io.github.sudharsan_selvaraj;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.collections.Lists;

import java.lang.reflect.Method;
import java.util.List;

public class MockDriverListener implements SpyDriverListener {

    private List<InvocationDetail> methodStack = Lists.newArrayList();

    public List<InvocationDetail> getInvocationStack() {
        return methodStack;
    }

    public InvocationDetail getLastInvocation() {
        return methodStack.get(methodStack.size() - 1);
    }

    @Override
    public void beforeDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args) {
        System.out.println(method.getDeclaringClass().getName() + " => " + method.getName());
        methodStack.add(new InvocationDetail(driver, method, args));
    }

    @Override
    public void afterDriverCommandExecuted(WebDriver driver, Object target, Method method, Object[] args, Object result) {
    }

    @Override
    public void onException(WebDriver driver, Object target, Method method, Object[] args, Throwable exception) {

    }


    @Override
    public void onException(WebDriver driver, WebElement target, Method method, Object[] args, Throwable exception) {

    }

    @Override
    public void beforeElementCommandExecuted(WebDriver driver, WebElement target, Method method, Object[] args) {
        System.out.println(method.getDeclaringClass().getName() + " => " + method.getName());
        methodStack.add(new InvocationDetail(driver, method, args));
    }

    @Override
    public void afterElementCommandExecuted(WebDriver driver, WebElement target, Method method, Object[] args, Object result) {

    }
}
