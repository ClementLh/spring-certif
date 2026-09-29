package com.clementlh.labs.equality;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PerformanceStore {

    private final LinkedHashMap<UUID, TrainingSet> trainingSets = new LinkedHashMap<>();

    void add(TrainingSet trainingSet) {
        trainingSets.put(trainingSet.id(), trainingSet);
        if (trainingSets.size() > 100) {
            trainingSets.keySet().iterator().remove();
        }

    }

    Optional<TrainingSet> findById(UUID id) {
        return Optional.ofNullable(trainingSets.get(id));
    }

    List<TrainingSet> findAll() {
        return new ArrayList<>(trainingSets.values());
    }

    int size() {
        return trainingSets.size();
    }
}
