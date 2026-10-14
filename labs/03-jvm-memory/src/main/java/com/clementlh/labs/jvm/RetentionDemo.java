package com.clementlh.labs.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Interactive local-only demo for observing heap retention with JDK diagnostic tools.
 */
public final class RetentionDemo {

    private static final int PAYLOADS_PER_BATCH = 8;
    private static final int PAYLOAD_SIZE_BYTES = 1024 * 1024;
    private static final PayloadRetentionRegistry REGISTRY = new PayloadRetentionRegistry();

    private RetentionDemo() {
    }

    public static void main(String[] args) throws IOException {
        addBatch();
        printState("Initial batch");

        System.out.printf("PID: %d%n", ProcessHandle.current().pid());
        System.out.println("Leave this process running while using jcmd from another terminal.");
        System.out.println("Commands: Enter/add = retain another 8 MiB, release = release all, quit = exit.");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String command;
            while ((command = reader.readLine()) != null) {
                switch (command.trim().toLowerCase(Locale.ROOT)) {
                    case "", "add" -> {
                        addBatch();
                        printState("After adding a batch");
                    }
                    case "release" -> {
                        REGISTRY.releaseAll();
                        printState("After releaseAll");
                    }
                    case "quit" -> {
                        System.out.println("Stopping demo.");
                        return;
                    }
                    default -> System.out.println("Unknown command. Use Enter/add, release or quit.");
                }
            }
        }
    }

    private static void addBatch() {
        for (int i = 0; i < PAYLOADS_PER_BATCH; i++) {
            REGISTRY.retainPayload(PAYLOAD_SIZE_BYTES);
        }
    }

    private static void printState(String event) {
        long retainedBytes = REGISTRY.retainedBytes();
        System.out.printf(
            "%s | payloads=%d | retainedBytes=%d | retainedMiB=%.1f%n",
            event,
            REGISTRY.retainedPayloadCount(),
            retainedBytes,
            retainedBytes / (1024.0 * 1024.0)
        );
    }
}
