package com.clementlh.labs.concurrency;

/**
 * Intentionally not thread-safe. Complete the TODOs for the race-condition experiment.
 */
public class UnsafeTrainingCounter implements TrainingCounter {

    private int count;

    @Override
    public void increment() {
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }
}
