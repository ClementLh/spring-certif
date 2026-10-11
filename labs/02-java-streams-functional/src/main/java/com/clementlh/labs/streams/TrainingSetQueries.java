package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Educational counterexample: subtraction is not associative, so this is not a valid general reduction.
     */
    public int subtractWithSequentialReduce(List<Integer> values) {
        return values.stream()
                .reduce(0, (result, value) -> result - value);
    }

    /**
     * Educational counterexample only: the result must not be relied on because subtraction is not associative.
     */
    public int subtractWithParallelReduce(List<Integer> values) {
        return values.parallelStream()
                .reduce(0, (result, value) -> result - value);
    }

    public Map<String, List<TrainingSet>> groupByExercise(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.groupingBy(TrainingSet::exercise));
    }

    public Map<String, Long> countSetsByExercise(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.groupingBy(
                        TrainingSet::exercise,
                        Collectors.counting()
                ));
    }

    public Map<String, Double> averageLoadByExercise(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.groupingBy(
                        TrainingSet::exercise,
                        Collectors.averagingDouble(TrainingSet::loadKg)
                ));
    }

    public Map<String, Double> maxLoadByExercise(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.groupingBy(
                        TrainingSet::exercise,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(TrainingSet::loadKg)),
                                maxSet -> maxSet.orElseThrow().loadKg()
                        )
                ));
    }

    public Map<String, List<Double>> loadsByExercise(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.groupingBy(
                        TrainingSet::exercise,
                        Collectors.mapping(
                                TrainingSet::loadKg,
                                Collectors.toList()
                        )
                ));
    }

    public Map<Boolean, List<TrainingSet>> partitionByLoadThreshold(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.partitioningBy(
                        trainingSet -> trainingSet.loadKg() >= 100.0
                ));
    }

    public Map<String, Double> maxLoadByExerciseWithToMap(List<TrainingSet> trainingSets) {
        return trainingSets.stream()
                .collect(Collectors.toMap(
                        TrainingSet::exercise,
                        TrainingSet::loadKg,
                        Double::max
                ));
    }
}
