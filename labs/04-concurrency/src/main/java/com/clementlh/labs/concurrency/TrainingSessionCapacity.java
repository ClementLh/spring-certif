package com.clementlh.labs.concurrency;

/**
 * Tracks reservations for a training session with a fixed capacity.
 *
 * TODO J5.3: make reserve() thread-safe while preserving the capacity invariant.
 */
public class TrainingSessionCapacity {

    private final int capacity;
    private int reserved;

    public TrainingSessionCapacity(int capacity) {
        // TODO validate capacity
        this.capacity = capacity;
    }

    /**
     * Attempts to reserve the requested number of places.
     *
     * @param places number of places to reserve; must be positive
     * @return true if the reservation succeeds, false if capacity is insufficient
     */
    public boolean reserve(int places) {
        // TODO validate places and make check-and-update atomic
        if (reserved + places > capacity) {
            return false;
        }
        reserved += places;
        return true;
    }

    public int reservedCount() {
        return reserved;
    }
}
