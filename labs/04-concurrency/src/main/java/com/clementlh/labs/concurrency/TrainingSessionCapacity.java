package com.clementlh.labs.concurrency;

/**
 * Tracks reservations for a training session with a fixed capacity.
 * <p>
 * TODO J5.3: make reserve() thread-safe while preserving the capacity invariant.
 */
public class TrainingSessionCapacity {

    private final int capacity;
    private int reserved;

    public TrainingSessionCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    /**
     * Attempts to reserve the requested number of places.
     *
     * @param places number of places to reserve; must be positive
     * @return true if the reservation succeeds, false if capacity is insufficient
     */
    public synchronized boolean reserve(int places) {

        if (places <= 0) {
            throw new IllegalArgumentException();
        }
        if (places > capacity - reserved) {
            return false;
        }
        reserved += places;
        return true;
    }

    public synchronized int reservedCount() {
        return reserved;
    }
}
