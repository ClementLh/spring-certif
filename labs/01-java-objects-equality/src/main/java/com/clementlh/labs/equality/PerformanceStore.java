package com.clementlh.labs.equality;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PerformanceStore {

    private static final int MAX_SIZE = 100;

    private final LinkedHashMap<UUID, TrainingSet> trainingSets = new LinkedHashMap<>();

    public void add(TrainingSet trainingSet) {
        Objects.requireNonNull(trainingSet);
        trainingSets.put(trainingSet.id(), trainingSet);

        if (trainingSets.size() > MAX_SIZE) {
            Iterator<UUID> iterator = trainingSets.keySet().iterator();
            iterator.next();
            iterator.remove();
        }
    }

    public Optional<TrainingSet> findById(UUID id) {
        return Optional.ofNullable(trainingSets.get(id));
    }

    public List<TrainingSet> findAll() {
        return new ArrayList<>(trainingSets.values());
    }

    public int size() {
        return trainingSets.size();
    }
}
