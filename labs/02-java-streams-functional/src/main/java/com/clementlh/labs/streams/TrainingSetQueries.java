package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;

import java.util.List;

public class TrainingSetQueries {

    public List<String> findUniqueExercisesAtLeast80Kg(List<TrainingSet> trainingSets) {

        return trainingSets.stream()
                .filter(trainingSet -> trainingSet.loadKg() >= 80.0)
                .map(TrainingSet::exercise)
                .distinct()
                .sorted()
                .toList();
    }
    public String concatenateValues(List<String> values) {
        return values.stream()
                .reduce("", String::concat, String::concat);
    }

    public double sumLoadsWithReduce(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .reduce(
                        0.0,
                        (totalLoad, trainingSet) -> totalLoad + trainingSet.loadKg(),
                        Double::sum
                );
    }

    public int subtractWithReduce(List<Integer> values) {

        return values.stream()
            .reduce(0, (result, value) -> result - value);
    }

    public int subtractWithParallel(List<Integer> values) {
        return values.parallelStream().reduce(0, (result, value) -> result - value);
    }

}
