package io.github.sudharsan_selvaraj;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SpyDriverOptions {
    private SpyDriverListener listener;
}
