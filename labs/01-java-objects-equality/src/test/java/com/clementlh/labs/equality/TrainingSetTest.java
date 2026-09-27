package com.clementlh.labs.equality;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingSetTest {

    @Test
    void same_training_set_content_should_be_equal() {
        TrainingSet first = new TrainingSet("Back Squat", 5, 90.0);
        TrainingSet second = new TrainingSet("Back Squat", 5, 90.0);

        assertThat(first).isEqualTo(second);
    }

    @Test
    void equal_training_sets_should_behave_as_one_value_in_a_set() {
        Set<TrainingSet> sets = new HashSet<>();
        sets.add(new TrainingSet("Bench Press", 5, 80.0));
        sets.add(new TrainingSet("Bench Press", 5, 80.0));

        assertThat(sets).hasSize(1);
    }
}
