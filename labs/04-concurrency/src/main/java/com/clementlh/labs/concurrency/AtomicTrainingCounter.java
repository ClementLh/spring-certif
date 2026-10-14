package com.clementlh.labs.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implement the counter with AtomicInteger; avoid adding an unnecessary lock.
 */
public class AtomicTrainingCounter implements TrainingCounter {

    private final AtomicInteger count = new AtomicInteger();

    @Override
    public void increment() {
        count.getAndIncrement();
    }

    @Override
    public int getCount() {
        return count.get();
    }
}
