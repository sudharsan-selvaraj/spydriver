package io.github.sudharsan_selvaraj.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class BaseCommand {
    private String id;
    private Method method;
    private Object[] arguments;
}
