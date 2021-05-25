package io.github.sudharsan_selvaraj;

import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;
import org.testng.collections.Lists;

import java.util.List;

public class MockDriverListener implements SpyDriverListener {

    public List<DriverCommand> driverCommandStack = Lists.newArrayList();
    public List<DriverCommandResult> driverCommandResultStack = Lists.newArrayList();
    public List<DriverCommandException> driverCommandExceptionStack = Lists.newArrayList();

    public List<ElementCommand> elementCommandStack = Lists.newArrayList();
    public List<ElementCommandResult> elementCommandResults = Lists.newArrayList();
    public List<ElementCommandException> elementCommandExceptionStack = Lists.newArrayList();


    public <T> T getLastInvocation(List<T> anyList) {
        return anyList.get(anyList.size() - 1);
    }

    @Override
    public void beforeDriverCommandExecuted(DriverCommand command) {
        System.out.println(command.getMethod().getDeclaringClass().getName() + " => " + command.getMethod().getName());
        driverCommandStack.add(command);
        //methodStack.add(new InvocationDetail(command.getDriver(), command.getMethod(), command.getArguments()));
    }

    @Override
    public void afterDriverCommandExecuted(DriverCommandResult command) {
        driverCommandResultStack.add(command);
    }

    @Override
    public void onException(DriverCommandException command) {
        driverCommandExceptionStack.add(command);
    }

    @Override
    public void beforeElementCommandExecuted(ElementCommand command) {
        System.out.println(command.getMethod().getDeclaringClass().getName() + " => " + command.getMethod().getName());
        elementCommandStack.add(command);
        // methodStack.add(new InvocationDetail(command.getDriver(), command.getMethod(), command.getArguments()));
    }

    @Override
    public void afterElementCommandExecuted(ElementCommandResult command) {
        elementCommandResults.add(command);
    }

    @Override
    public void onException(ElementCommandException command) {
        elementCommandExceptionStack.add(command);
    }
}
