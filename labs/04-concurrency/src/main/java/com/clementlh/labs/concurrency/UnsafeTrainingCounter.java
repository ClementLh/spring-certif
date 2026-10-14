package com.clementlh.labs.concurrency;

/**
 * Intentionally not thread-safe. Complete the TODOs for the race-condition experiment.
 */
public class UnsafeTrainingCounter implements TrainingCounter {

    private int count;

    @Override
    public void increment() {
        throw new UnsupportedOperationException("TODO: implement a plain increment");
    }

    @Override
    public int getCount() {
        throw new UnsupportedOperationException("TODO: return the current count");
    }
}
