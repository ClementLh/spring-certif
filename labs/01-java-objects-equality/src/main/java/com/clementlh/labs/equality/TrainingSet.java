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


    // Pour considérer que 2 objets sont "égaux" on regarde si leurs attributs sont égaux,
    // Si c'est le cas même s'ils n'ont pas la meme ref ils seront considérés comme égaux
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrainingSet that = (TrainingSet) obj;
        return repetitions == that.repetitions &&
                Double.compare(loadKg, that.loadKg) == 0 &&
                Objects.equals(exercise, that.exercise);
    }

    // En fait si on instancie 2 objets différents, mais avec les mêmes attributs, alors ils auront le même hash
    // Donc si on ajoutes 2 objets différents mais avec les mêmes attributs dans un HashSet, il n'y aura qu'un seul élément dans le HashSet
    @Override
    public int hashCode() {
        return Objects.hash(exercise, repetitions, loadKg);
    }
}
