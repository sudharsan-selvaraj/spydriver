package io.github.sudharsan_selvaraj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class InvocationDetail {
    private WebDriver driver;
    private Method method;
    private Object[] arguments;
}
