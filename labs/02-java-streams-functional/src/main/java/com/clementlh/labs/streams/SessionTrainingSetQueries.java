package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionTrainingSetQueries {

    public List<String> findUniqueExercisesAtLeast100Kg(List<List<TrainingSet>> sessions) {
        return sessions.stream()
            .flatMap(List::stream)
            .filter(set -> set.loadKg() >= 100.0)
            .map(TrainingSet::exercise)
            .distinct()
            .sorted()
            .toList();
    }

    public double sumLoads(List<List<TrainingSet>> sessions) {
        return sessions.stream()
            .flatMap(List::stream)
            .mapToDouble(TrainingSet::loadKg)
            .sum();
    }

    public Optional<TrainingSet> findHeaviestSet(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream)
            .max(Comparator.comparingDouble(TrainingSet::loadKg));
    }

    public Map<String, Long> countSetsByExercise(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream).collect(
            Collectors.groupingBy(TrainingSet::exercise, Collectors.counting())
        );
    }

    public Map<String, Double> averageLoadByExercise(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream).collect(
            Collectors.groupingBy(TrainingSet::exercise, Collectors.averagingDouble(TrainingSet::loadKg))
        );
    }

    public Map<Boolean, List<TrainingSet>> partitionByLoadThreshold(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream).collect(Collectors.partitioningBy(set -> set.loadKg() >= 100.0));
    }

    public Map<String, List<Double>> loadsByExercise(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream).collect(
            Collectors.groupingBy(TrainingSet::exercise, Collectors.mapping(TrainingSet::loadKg, Collectors.toList()))
        );
    }

    public Optional<TrainingSet> findFirstHeavySetInFirstSessionContainingOne(List<List<TrainingSet>> sessions) {
        return sessions.stream().flatMap(List::stream).filter(set -> set.loadKg() >= 100.0).findFirst();
    }
}
