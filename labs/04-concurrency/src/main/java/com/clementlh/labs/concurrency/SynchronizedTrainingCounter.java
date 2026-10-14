package com.clementlh.labs.concurrency;

/**
 * Implement the counter with a monitor so concurrent increments are not lost.
 */
public class SynchronizedTrainingCounter implements TrainingCounter {

    private int count;

    @Override
    public void increment() {
        throw new UnsupportedOperationException("TODO: protect the increment");
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("TODO: read the count consistently");
    }
}
