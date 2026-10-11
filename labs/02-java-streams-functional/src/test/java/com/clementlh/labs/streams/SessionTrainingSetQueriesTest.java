package com.clementlh.labs.streams;

import com.clementlh.labs.equality.TrainingSet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Retirer cette annotation après implémentation de SessionTrainingSetQueries")
class SessionTrainingSetQueriesTest {

    private final SessionTrainingSetQueries queries = new SessionTrainingSetQueries();

    @Test
    void should_return_unique_exercises_at_least_100_kg_sorted_alphabetically() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(
                        new TrainingSet("Back Squat", 5, 90.0),
                        new TrainingSet("Back Squat", 5, 100.0),
                        new TrainingSet("Bench Press", 5, 80.0)
                ),
                List.of(
                        new TrainingSet("Deadlift", 3, 140.0),
                        new TrainingSet("Back Squat", 3, 110.0)
                )
        );

        assertThat(queries.findUniqueExercisesAtLeast100Kg(sessions))
                .containsExactly("Back Squat", "Deadlift");
    }

    @Test
    void should_sum_loads_across_all_sessions() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(
                        new TrainingSet("Back Squat", 5, 90.0),
                        new TrainingSet("Back Squat", 5, 100.0)
                ),
                List.of(
                        new TrainingSet("Bench Press", 5, 80.0),
                        new TrainingSet("Deadlift", 3, 140.0),
                        new TrainingSet("Back Squat", 3, 110.0)
                )
        );

        assertThat(queries.sumLoads(sessions)).isEqualTo(520.0);
    }

    @Test
    void should_find_heaviest_training_set_across_all_sessions() {
        TrainingSet heaviest = new TrainingSet("Deadlift", 3, 180.0);
        List<List<TrainingSet>> sessions = List.of(
                List.of(new TrainingSet("Back Squat", 5, 100.0)),
                List.of(new TrainingSet("Bench Press", 5, 90.0), heaviest)
        );

        assertThat(queries.findHeaviestSet(sessions))
                .contains(heaviest);
    }

    @Test
    void should_return_empty_when_no_heaviest_set_exists() {
        assertThat(queries.findHeaviestSet(List.of()))
                .isEmpty();
    }

    @Test
    void should_count_training_sets_by_exercise_across_sessions() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(
                        new TrainingSet("Back Squat", 5, 100.0),
                        new TrainingSet("Back Squat", 3, 110.0)
                ),
                List.of(
                        new TrainingSet("Deadlift", 3, 140.0),
                        new TrainingSet("Bench Press", 5, 80.0)
                )
        );

        assertThat(queries.countSetsByExercise(sessions))
                .containsEntry("Back Squat", 2L)
                .containsEntry("Deadlift", 1L)
                .containsEntry("Bench Press", 1L);
    }

    @Test
    void should_average_load_by_exercise_across_sessions() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(
                        new TrainingSet("Back Squat", 5, 100.0),
                        new TrainingSet("Back Squat", 3, 110.0)
                ),
                List.of(
                        new TrainingSet("Deadlift", 3, 140.0),
                        new TrainingSet("Bench Press", 5, 80.0)
                )
        );

        assertThat(queries.averageLoadByExercise(sessions))
                .containsEntry("Back Squat", 105.0)
                .containsEntry("Deadlift", 140.0)
                .containsEntry("Bench Press", 80.0);
    }

    @Test
    void should_partition_all_sets_by_the_100_kg_threshold() {
        TrainingSet light = new TrainingSet("Bench Press", 5, 80.0);
        TrainingSet exactly100 = new TrainingSet("Back Squat", 5, 100.0);
        TrainingSet heavy = new TrainingSet("Deadlift", 3, 140.0);
        List<List<TrainingSet>> sessions = List.of(
                List.of(light, exactly100),
                List.of(heavy)
        );

        Map<Boolean, List<TrainingSet>> result = queries.partitionByLoadThreshold(sessions);

        assertThat(result.get(false)).containsExactly(light);
        assertThat(result.get(true)).containsExactly(exactly100, heavy);
    }

    @Test
    void should_collect_loads_by_exercise_in_encounter_order() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(
                        new TrainingSet("Back Squat", 5, 100.0),
                        new TrainingSet("Deadlift", 3, 140.0)
                ),
                List.of(
                        new TrainingSet("Back Squat", 3, 110.0)
                )
        );

        assertThat(queries.loadsByExercise(sessions))
                .containsEntry("Back Squat", List.of(100.0, 110.0))
                .containsEntry("Deadlift", List.of(140.0));
    }

    @Test
    void should_find_first_heavy_set_from_the_first_session_containing_one() {
        TrainingSet firstHeavy = new TrainingSet("Bench Press", 5, 100.0);
        TrainingSet laterHeavySameSession = new TrainingSet("Bench Press", 3, 120.0);
        TrainingSet heavierLaterSession = new TrainingSet("Deadlift", 3, 180.0);

        List<List<TrainingSet>> sessions = List.of(
                List.of(new TrainingSet("Back Squat", 5, 80.0)),
                List.of(firstHeavy, laterHeavySameSession),
                List.of(heavierLaterSession)
        );

        assertThat(queries.findFirstHeavySetInFirstSessionContainingOne(sessions))
                .contains(firstHeavy);
    }

    @Test
    void should_return_empty_when_no_session_has_a_heavy_set() {
        List<List<TrainingSet>> sessions = List.of(
                List.of(new TrainingSet("Back Squat", 5, 80.0)),
                List.of(new TrainingSet("Bench Press", 5, 90.0))
        );

        assertThat(queries.findFirstHeavySetInFirstSessionContainingOne(sessions))
                .isEmpty();
    }
}
