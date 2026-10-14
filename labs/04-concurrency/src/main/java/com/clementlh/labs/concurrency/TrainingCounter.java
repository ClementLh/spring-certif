package com.clementlh.labs.concurrency;

/**
 * Contract used to compare thread-safe and intentionally unsafe counters.
 */
public interface TrainingCounter {

    void increment();

    int getCount();
}
