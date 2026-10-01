package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Activer après implémentation de TrainingSetQueries")
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
    void should_sort_exercise_names_alphabetically() {
        List<TrainingSet> trainingSets = List.of(
                new TrainingSet("Deadlift", 5, 140.0),
                new TrainingSet("Back Squat", 5, 100.0),
                new TrainingSet("Bench Press", 5, 90.0)
        );

        assertThat(queries.findUniqueExercisesAtLeast80Kg(trainingSets))
                .containsExactly("Back Squat", "Bench Press", "Deadlift");
    }
}
