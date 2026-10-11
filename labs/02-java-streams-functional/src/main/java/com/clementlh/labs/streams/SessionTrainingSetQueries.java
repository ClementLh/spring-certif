package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SessionTrainingSetQueries {

    public List<String> findUniqueExercisesAtLeast100Kg(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public double sumLoads(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Optional<TrainingSet> findHeaviestSet(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Map<String, Long> countSetsByExercise(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Map<String, Double> averageLoadByExercise(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Map<Boolean, List<TrainingSet>> partitionByLoadThreshold(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Map<String, List<Double>> loadsByExercise(List<List<TrainingSet>> sessions) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }

    public Optional<TrainingSet> findFirstHeavySetInFirstSessionContainingOne(
            List<List<TrainingSet>> sessions
    ) {
        throw new UnsupportedOperationException("TODO: implement final J3 exercise");
    }
}
