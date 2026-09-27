package com.clementlh.labs.equality;

import java.util.Objects;
import java.util.UUID;

public final class TrainingSet {

    private final UUID id;
    private final String exercise;
    private final int repetitions;
    private final double loadKg;

    public TrainingSet(String exercise, int repetitions, double loadKg) {
        this(UUID.randomUUID(), exercise, repetitions, loadKg);
    }

    TrainingSet(UUID id, String exercise, int repetitions, double loadKg) {
        this.id = Objects.requireNonNull(id);
        this.exercise = Objects.requireNonNull(exercise);
        this.repetitions = repetitions;
        this.loadKg = loadKg;
    }

    public UUID id() {
        return id;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        TrainingSet that = (TrainingSet) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
