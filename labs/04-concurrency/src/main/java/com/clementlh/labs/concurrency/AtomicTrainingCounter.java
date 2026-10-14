package com.clementlh.labs.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implement the counter with AtomicInteger; avoid adding an unnecessary lock.
 */
public class AtomicTrainingCounter implements TrainingCounter {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public void increment() {
        throw new UnsupportedOperationException("TODO: use an atomic increment");
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("TODO: return the current count");
    }
}
