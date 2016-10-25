package com.msl.pbossIITestForJmeter.ec.request.impl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhaofeng on 2016/10/13.
 */
public class TransceiverThreadFactory implements ThreadFactory {
    private final AtomicInteger threadId = new AtomicInteger(0);
    private final String prefix;

    public TransceiverThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.setName(prefix + " " + threadId.incrementAndGet());
        return thread;
    }
}