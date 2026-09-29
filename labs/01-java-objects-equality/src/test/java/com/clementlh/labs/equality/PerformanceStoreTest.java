package com.clementlh.labs.equality;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceStoreTest {


    @Test
    void addTrainingSet() {
        TrainingSet trainingSet = new TrainingSet("Back Squat", 5, 90.0);
        PerformanceStore store = new PerformanceStore();
        store.add(trainingSet);

        store.findById(trainingSet.id()).ifPresentOrElse(
                found -> assertEquals(trainingSet, found),
                () -> fail("Training set not found")
        );
    }

    @Test
    void findAllTrainingSets() {
        TrainingSet trainingSet = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet trainingSet2 = new TrainingSet("Front Squat", 5, 85.0);

        PerformanceStore store = new PerformanceStore();
        store.add(trainingSet);
        store.add(trainingSet2);

        List<TrainingSet> all = store.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(trainingSet));
        assertTrue(all.contains(trainingSet2));
    }

    @Test
    void getSize() {
        PerformanceStore store = new PerformanceStore();

        TrainingSet trainingSet = new TrainingSet("Back Squat", 5, 90.0);

        store.add(trainingSet);
        store.size();

        assertEquals(1, store.size());
    }

}
