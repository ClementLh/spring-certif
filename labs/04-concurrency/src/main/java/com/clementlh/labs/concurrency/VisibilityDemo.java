package com.clementlh.labs.concurrency;

/**
 * J5.4 — Visibility and the Java Memory Model.
 *
 * TODO: implement the two experiments described in
 * exercises/04-concurrency/J5.4-VISIBILITY.md.
 */
public class VisibilityDemo {

    /*
     * Experiment A: ordinary shared state.
     *
     * TODO: use this flag in a worker loop and modify it from the main thread.
     * Do not assume the worker must remain stuck: that outcome is not guaranteed.
     */
    private boolean running;

    /*
     * Experiment B: volatile shared state.
     *
     * TODO: implement the same experiment using this flag and explain the
     * visibility guarantee that volatile provides.
     */
    private volatile boolean runningVolatile;

    public static void main(String[] args) throws InterruptedException {
        VisibilityDemo demo = new VisibilityDemo();

        // TODO J5.4: run both experiments with bounded waiting and clean shutdown.
        // TODO: print enough information to compare the observations.
        // Do not use Thread.sleep() to try to force a particular race outcome.
    }

    /**
     * TODO: implement the experiment with the ordinary boolean flag.
     *
     * Suggested structure:
     * - set running to true;
     * - start a worker that loops while running is true;
     * - update running from the calling thread;
     * - wait for the worker with a timeout and handle timeout safely.
     */
    private void runWithoutVolatile() throws InterruptedException {
        // TODO J5.4
    }

    /**
     * TODO: implement the equivalent experiment with the volatile flag.
     *
     * The worker should observe the stop request without relying on a lock.
     * Ensure the worker is stopped and joined even if an assertion or timeout
     * occurs.
     */
    private void runWithVolatile() throws InterruptedException {
        // TODO J5.4
    }
}
