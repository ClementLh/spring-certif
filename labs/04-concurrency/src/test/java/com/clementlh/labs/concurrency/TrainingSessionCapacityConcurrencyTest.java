package com.clementlh.labs.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TrainingSessionCapacityConcurrencyTest {

    private static final int CAPACITY = 100;
    private static final int THREAD_COUNT = 20;
    private static final int ATTEMPTS_PER_THREAD = 100;

    @Test
    @Disabled("J5.3: remove once reserve() preserves the invariant under concurrency")
    void concurrent_reservations_must_never_exceed_capacity() throws Exception {
        TrainingSessionCapacity session = new TrainingSessionCapacity(CAPACITY);
        AtomicInteger successfulReservations = new AtomicInteger();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startSignal = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                futures.add(executor.submit(() -> {
                    startSignal.await();
                    for (int j = 0; j < ATTEMPTS_PER_THREAD; j++) {
                        if (session.reserve(1)) {
                            successfulReservations.incrementAndGet();
                        }
                    }
                    return null;
                }));
            }

            startSignal.countDown();
            for (Future<?> future : futures) {
                future.get(30, TimeUnit.SECONDS);
            }

            assertThat(successfulReservations.get()).isEqualTo(CAPACITY);
            assertThat(session.reservedCount()).isEqualTo(CAPACITY);
            assertThat(session.reservedCount()).isLessThanOrEqualTo(CAPACITY);
        } finally {
            startSignal.countDown();
            executor.shutdownNow();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Executor did not terminate in time");
            }
        }
    }

    @Test
    @Disabled("J5.3: remove once constructor validation is implemented")
    void capacity_must_be_positive() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> new TrainingSessionCapacity(0));
    }

    @Test
    @Disabled("J5.3: remove once reservation validation is implemented")
    void reservation_quantity_must_be_positive() {
        TrainingSessionCapacity session = new TrainingSessionCapacity(CAPACITY);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> session.reserve(0));
    }
}
