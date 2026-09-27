package com.clementlh.labs.equality;

import java.util.Objects;

public final class TrainingSet {

    private final String exercise;
    private final int repetitions;
    private final double loadKg;

    public TrainingSet(String exercise, int repetitions, double loadKg) {
        this.exercise = Objects.requireNonNull(exercise);
        this.repetitions = repetitions;
        this.loadKg = loadKg;
    }

    public String exercise() {
        return exercise;
    }

    public int repetitions() {
        return repetitions;
    }

    public double loadKg() {
        return loadKg;
    }
}
