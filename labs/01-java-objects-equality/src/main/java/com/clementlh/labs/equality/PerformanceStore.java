package com.clementlh.labs.equality;

import java.util.*;

public class PerformanceStore {

    private final LinkedHashMap<UUID, TrainingSet> trainingSets = new LinkedHashMap<>();

    void add(TrainingSet trainingSet) {
        trainingSets.put(UUID.randomUUID(), trainingSet);
        if (trainingSets.size() > 100) {
            trainingSets.remove(trainingSets.keySet().iterator().next());
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
