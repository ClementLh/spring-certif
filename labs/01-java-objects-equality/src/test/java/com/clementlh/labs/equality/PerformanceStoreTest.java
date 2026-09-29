package com.clementlh.labs.equality;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceStoreTest {

    @Test
    void should_find_training_set_by_id() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet trainingSet = new TrainingSet("Back Squat", 5, 90.0);

        store.add(trainingSet);

        assertTrue(store.findById(trainingSet.id()).isPresent());
        assertEquals(trainingSet, store.findById(trainingSet.id()).orElseThrow());
    }

    @Test
    void should_return_empty_when_training_set_does_not_exist() {
        PerformanceStore store = new PerformanceStore();

        assertTrue(store.findById(UUID.randomUUID()).isEmpty());
    }

    @Test
    void should_preserve_insertion_order() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Front Squat", 5, 85.0);

        store.add(first);
        store.add(second);

        assertEquals(List.of(first, second), store.findAll());
    }

    @Test
    void should_keep_duplicate_training_set_contents_as_distinct_entities() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Back Squat", 5, 90.0);

        store.add(first);
        store.add(second);

        assertNotEquals(first.id(), second.id());
        assertEquals(2, store.size());
        assertEquals(List.of(first, second), store.findAll());
    }

    @Test
    void should_keep_at_most_100_training_sets() {
        PerformanceStore store = new PerformanceStore();

        for (int i = 0; i < 101; i++) {
            store.add(new TrainingSet("Exercise " + i, 5, i));
        }

        assertEquals(100, store.size());
    }

    @Test
    void should_evict_oldest_training_set_when_adding_101st_element() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet first = null;
        TrainingSet second = null;
        TrainingSet last = null;

        for (int i = 0; i < 101; i++) {
            TrainingSet trainingSet = new TrainingSet("Exercise " + i, 5, i);

            if (i == 0) {
                first = trainingSet;
            }
            if (i == 1) {
                second = trainingSet;
            }
            if (i == 100) {
                last = trainingSet;
            }

            store.add(trainingSet);
        }

        assertFalse(store.findById(first.id()).isPresent());
        assertTrue(store.findById(second.id()).isPresent());
        assertTrue(store.findById(last.id()).isPresent());
    }

    @Test
    void should_not_expose_internal_collection_when_find_all_result_is_modified() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Front Squat", 5, 85.0);

        store.add(first);
        store.add(second);

        List<TrainingSet> result = store.findAll();
        result.clear();

        assertEquals(2, store.size());
        assertEquals(List.of(first, second), store.findAll());
    }

    @Test
    void should_return_zero_when_store_is_empty() {
        PerformanceStore store = new PerformanceStore();

        assertEquals(0, store.size());
    }

    @Test
    void should_return_current_size_after_adding_training_sets() {
        PerformanceStore store = new PerformanceStore();
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Front Squat", 5, 85.0);

        store.add(first);
        assertEquals(1, store.size());

        store.add(second);
        assertEquals(2, store.size());
    }
}
