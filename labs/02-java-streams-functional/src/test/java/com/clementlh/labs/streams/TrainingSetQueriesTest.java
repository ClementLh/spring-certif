package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
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

    @Test
    void should_group_training_sets_by_exercise() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 110.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        Map<String, List<TrainingSet>> grouped = queries.groupByExercise(trainingSets);

        assertThat(grouped.get("Back Squat")).containsExactly(trainingSets.get(0), trainingSets.get(1));
        assertThat(grouped.get("Deadlift")).containsExactly(trainingSets.get(2));
    }

    @Test
    void should_count_training_sets_by_exercise() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 110.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.countSetsByExercise(trainingSets))
                .containsEntry("Back Squat", 2L)
                .containsEntry("Deadlift", 1L);
    }

    @Test
    void should_average_load_by_exercise() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 110.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.averageLoadByExercise(trainingSets))
                .containsEntry("Back Squat", 105.0)
                .containsEntry("Deadlift", 140.0);
    }

    @Test
    void should_collect_loads_by_exercise_with_mapping() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 110.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.loadsByExercise(trainingSets))
                .containsEntry("Back Squat", List.of(100.0, 110.0))
                .containsEntry("Deadlift", List.of(140.0));
    }

    @Test
    void should_partition_training_sets_by_100_kg_threshold() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 80.0),
                new TrainingSet("Back Squat", 3, 100.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.partitionByLoadThreshold(trainingSets))
                .containsEntry(false, List.of(trainingSets.get(0)))
                .containsEntry(true, List.of(trainingSets.get(1), trainingSets.get(2)));
    }

    @Test
    void should_find_max_load_by_exercise_with_to_map() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Back Squat", 3, 120.0),
                new TrainingSet("Deadlift", 3, 140.0)
        );

        assertThat(queries.maxLoadByExerciseWithToMap(trainingSets))
                .containsEntry("Back Squat", 120.0)
                .containsEntry("Deadlift", 140.0);
    }
}
