package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TrainingSetQueriesTest {

    private final TrainingSetQueries queries = new TrainingSetQueries();

    @Test
    void should_keep_exercises_at_least_80_kg() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 79.0),
                new TrainingSet("Bench Press", 5, 80.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.findUniqueExercisesAtLeast80Kg(trainingSets))
                .containsExactly("Bench Press", "Deadlift");
    }

    @Test
    void should_remove_duplicate_exercise_names() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 100.0)
        );

        assertThat(queries.findUniqueExercisesAtLeast80Kg(trainingSets))
                .containsExactly("Back Squat");
    }

    @Test
    void should_concatenate_string_values_with_reduce() {
        assertThat(queries.concatenateValues(List.of("A", "B", "C", "D")))
                .isEqualTo("ABCD");
    }

    @Test
    void should_return_empty_string_when_concatenating_empty_values() {
        assertThat(queries.concatenateValues(List.of()))
                .isEmpty();
    }

    @Test
    void should_sum_training_set_loads_with_reduce() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Deadlift", 3, 140.0),
                new TrainingSet("Bench Press", 5, 80.0)
        );

        assertThat(queries.sumLoadsWithReduce(trainingSets))
                .isEqualTo(320.0);
    }

    @Test
    void should_return_zero_when_summing_empty_training_sets_with_reduce() {
        assertThat(queries.sumLoadsWithReduce(List.of()))
                .isEqualTo(0.0);
    }

    @Test
    void should_sort_exercise_names_alphabetically() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Deadlift", 5, 140.0),
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Bench Press", 5, 90.0)
        );

        assertThat(queries.findUniqueExercisesAtLeast80Kg(trainingSets))
                .containsExactly("Back Squat", "Bench Press", "Deadlift");
    }

    @Test
    void should_demonstrate_different_results_between_sequential_and_parallel_reduce() {
        List<Integer> integers = IntStream.rangeClosed(1, 100)
                .boxed()
                .toList();

        int sequential = queries.subtractWithSequentialReduce(integers);
        int parallel = queries.subtractWithParallelReduce(integers);

        assertThat(sequential).isEqualTo(-5050);
        assertNotEquals(sequential, parallel);
    }
}
