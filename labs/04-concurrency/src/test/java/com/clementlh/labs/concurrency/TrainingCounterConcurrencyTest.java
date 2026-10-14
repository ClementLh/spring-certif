package com.clementlh.labs.concurrency;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingCounterConcurrencyTest {

    private static final int THREAD_COUNT = 10;
    private static final int INCREMENTS_PER_THREAD = 100_000;
    private static final int EXPECTED_COUNT = THREAD_COUNT * INCREMENTS_PER_THREAD;

    @Test
    @Disabled("À activer après l'implémentation de SynchronizedTrainingCounter")
    void synchronized_counter_must_not_lose_increments() throws Exception {
        assertThat(runConcurrentIncrements(new SynchronizedTrainingCounter()))
                .isEqualTo(EXPECTED_COUNT);
    }

    @Test
    @Disabled("À activer après l'implémentation de AtomicTrainingCounter")
    void atomic_counter_must_not_lose_increments() throws Exception {
        assertThat(runConcurrentIncrements(new AtomicTrainingCounter()))
                .isEqualTo(EXPECTED_COUNT);
    }

    @Test
    @Disabled("Test de diagnostic uniquement : observer le résultat sans exiger une perte à chaque exécution")
    void unsafe_counter_experiment() throws Exception {
        int observed = runConcurrentIncrements(new UnsafeTrainingCounter());

        System.out.printf("Unsafe counter: observed=%d, expected=%d%n",
                observed, EXPECTED_COUNT);

        // This range check does not assert that a race must be observed.
        assertThat(observed).isBetween(0, EXPECTED_COUNT);
    }

    private int runConcurrentIncrements(TrainingCounter counter) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startSignal = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                futures.add(executor.submit(() -> {
                    startSignal.await();

                    for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                        counter.increment();
                    }

                    return null;
                }));
            }

            // All workers are submitted before they are released to start together.
            startSignal.countDown();

            // get() waits for completion and propagates failures from worker tasks.
            for (Future<?> future : futures) {
                future.get(30, TimeUnit.SECONDS);
            }

            return counter.getCount();
        } finally {
            // Also release workers if submission or waiting fails.
            startSignal.countDown();
            executor.shutdownNow();

            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Executor did not terminate in time");
            }
        }
    }
}
