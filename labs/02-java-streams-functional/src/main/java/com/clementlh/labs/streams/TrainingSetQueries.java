package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;

import java.util.List;

public class TrainingSetQueries {

    public List<String> findUniqueExercisesAtLeast80Kg(List<TrainingSet> trainingSets) {

        return trainingSets.stream().filter(trainingSet -> trainingSet.loadKg() >= 80.0)
                .map(TrainingSet::exercise)
                .distinct()
                .sorted()
                .toList();
    }
}
