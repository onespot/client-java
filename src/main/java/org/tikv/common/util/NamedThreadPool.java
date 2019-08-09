package org.tikv.common.util;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

public class NamedThreadPool implements ThreadFactory {
    private final String template;
    private AtomicLong threadNum = new AtomicLong();

    private NamedThreadPool(final String template) {
        this.template = template;
    }

    @Nonnull
    public static ThreadFactory create(final String template) {
        final String check = String.format(template, 1);
        requireNonNull(check, "making sure that a template string was passed in");
        return new NamedThreadPool(template);
    }

    @Override
    public Thread newThread(@Nonnull final Runnable r) {
        return new Thread(r, String.format(template, threadNum.getAndIncrement()));
    }
}
