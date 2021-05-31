package io.github.sudharsan_selvaraj;

import io.github.sudharsan_selvaraj.types.driver.DriverCommand;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandException;
import io.github.sudharsan_selvaraj.types.driver.DriverCommandResult;
import io.github.sudharsan_selvaraj.types.element.ElementCommand;
import io.github.sudharsan_selvaraj.types.element.ElementCommandException;
import io.github.sudharsan_selvaraj.types.element.ElementCommandResult;

import java.util.ArrayList;
import java.util.List;

public class DriverEventDispatcher implements SpyDriverListener {

    private List<SpyDriverListener> listeners;

    public DriverEventDispatcher() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(SpyDriverListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void beforeDriverCommandExecuted(DriverCommand command) {
        listeners.forEach(l -> l.beforeDriverCommandExecuted(command));
    }

    @Override
    public void afterDriverCommandExecuted(DriverCommandResult command) {
        listeners.forEach(l -> l.afterDriverCommandExecuted(command));
    }

    @Override
    public void onException(DriverCommandException command) {
        listeners.forEach(l -> l.onException(command));
    }

    @Override
    public void beforeElementCommandExecuted(ElementCommand command) {
        listeners.forEach(l -> l.beforeElementCommandExecuted(command));
    }

    @Override
    public void afterElementCommandExecuted(ElementCommandResult command) {
        listeners.forEach(l -> l.afterElementCommandExecuted(command));
    }

    @Override
    public void onException(ElementCommandException command) {
        listeners.forEach(l -> l.onException(command));
    }
}
