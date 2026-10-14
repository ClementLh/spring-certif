package com.clementlh.labs.concurrency;

/**
 * Implement the counter with a monitor so concurrent increments are not lost.
 */
public class SynchronizedTrainingCounter implements TrainingCounter {

    private int count;

    @Override
    public synchronized void increment() {
        count++;
    }

    @Override
    public synchronized int getCount() {
        return count;
    }
}
