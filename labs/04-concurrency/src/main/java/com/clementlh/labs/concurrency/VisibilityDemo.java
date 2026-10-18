package com.clementlh.labs.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * J5.4 — Visibility and the Java Memory Model.
 *
 * Run both experiments and compare the observations. The ordinary-boolean
 * experiment intentionally contains a data race; its outcome is not guaranteed.
 */
public class VisibilityDemo {

    private static final long JOIN_TIMEOUT_MILLIS = 1_000L;

    private boolean running;
    private volatile boolean runningVolatile;

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo demo = new VisibilityDemo();

        System.out.println("=== Experiment A: ordinary boolean ===");
        demo.runWithoutVolatile();

        System.out.println();
        System.out.println("=== Experiment B: volatile boolean ===");
        demo.runWithVolatile();
    }

    /**
     * Demonstrates that a write to an ordinary shared field is not guaranteed
     * to become visible to another thread in the absence of synchronization.
     *
     * An interrupt is used only as a cleanup fallback so the demonstration
     * cannot leave a worker running indefinitely.
     */
    private void runWithoutVolatile() throws InterruptedException {
        running = true;
        CountDownLatch started = new CountDownLatch(1);
        AtomicBoolean stoppedByFlag = new AtomicBoolean(false);

        Thread worker = new Thread(() -> {
            started.countDown();
            while (running && !Thread.currentThread().isInterrupted()) {
                Thread.onSpinWait();
            }
            stoppedByFlag.set(!running);
        }, "ordinary-flag-worker");

        // Daemon is an additional safety net if the runtime/JIT does not make
        // the racy flag write visible and the interrupt fallback fails.
        worker.setDaemon(true);
        worker.start();

        if (!started.await(JOIN_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
            worker.interrupt();
            worker.join(JOIN_TIMEOUT_MILLIS);
            throw new IllegalStateException("Worker did not start in time");
        }

        running = false;
        worker.join(JOIN_TIMEOUT_MILLIS);

        if (worker.isAlive()) {
            System.out.println("Worker did not finish after the ordinary flag write.");
            System.out.println("Requesting interrupt for bounded cleanup.");
            worker.interrupt();
            worker.join(JOIN_TIMEOUT_MILLIS);
        } else {
            System.out.println("Worker finished after the ordinary flag write.");
        }

        System.out.println("Worker stopped: " + !worker.isAlive());
        System.out.println("Stop observed through ordinary flag: " + stoppedByFlag.get());
        System.out.println("Note: either outcome is possible; this experiment is not a deterministic test.");

        if (worker.isAlive()) {
            System.out.println("Cleanup fallback did not terminate the worker; it is a daemon thread.");
        }
    }

    /**
     * Demonstrates that a write to a volatile field is visible to subsequent
     * reads of that field by other threads.
     */
    private void runWithVolatile() throws InterruptedException {
        runningVolatile = true;
        CountDownLatch started = new CountDownLatch(1);

        Thread worker = new Thread(() -> {
            started.countDown();
            while (runningVolatile) {
                Thread.onSpinWait();
            }
        }, "volatile-flag-worker");

        worker.start();

        if (!started.await(JOIN_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
            runningVolatile = false;
            worker.interrupt();
            worker.join(JOIN_TIMEOUT_MILLIS);
            throw new IllegalStateException("Worker did not start in time");
        }

        runningVolatile = false;
        worker.join(JOIN_TIMEOUT_MILLIS);

        if (worker.isAlive()) {
            // A volatile stop flag should be sufficient here. Interrupt is a
            // last-resort cleanup path, not part of the visibility experiment.
            worker.interrupt();
            worker.join(JOIN_TIMEOUT_MILLIS);
            throw new IllegalStateException("Worker did not stop after volatile flag was cleared");
        }

        System.out.println("Worker finished after the volatile flag write.");
        System.out.println("Worker stopped: " + !worker.isAlive());
    }
}
