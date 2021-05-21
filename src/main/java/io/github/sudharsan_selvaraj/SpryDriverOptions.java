package io.github.sudharsan_selvaraj;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SpryDriverOptions {
    private SpyDriverListener listener;
}
