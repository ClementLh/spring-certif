package com.clementlh.labs.equality;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingSetTest {

    @Test
    void same_instance_should_be_equal() {
        TrainingSet trainingSet = new TrainingSet("Back Squat", 5, 90.0);

        assertThat(trainingSet).isEqualTo(trainingSet);
    }

    @Test
    void different_instances_with_same_content_should_not_be_equal() {
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Back Squat", 5, 90.0);

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void instances_with_same_id_should_be_equal() {
        UUID id = UUID.randomUUID();

        TrainingSet first = new TrainingSet(id, "Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet(id, "Bench Press", 10, 80.0);

        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    void different_ids_should_not_be_equal_even_with_same_content() {
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Back Squat", 5, 90.0);

        assertThat(first.id()).isNotEqualTo(second.id());
        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void identical_training_sets_should_be_kept_as_two_entities_in_a_set() {
        Set<TrainingSet> sets = new HashSet<>();
        sets.add(new TrainingSet("Bench Press", 5, 80.0));
        sets.add(new TrainingSet("Bench Press", 5, 80.0));

        assertThat(sets).hasSize(2);
    }
}
