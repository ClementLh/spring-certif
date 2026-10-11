package com.clementlh.labs.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise: model objects kept alive by strong references in a long-lived registry.
 */
public final class PayloadRetentionRegistry {

    private final List<byte[]> retainedPayloads = new ArrayList<>();

    public void retainPayload(int sizeInBytes) {
        throw new UnsupportedOperationException("TODO: implement J4 retention exercise");
    }

    public void releaseAll() {
        throw new UnsupportedOperationException("TODO: implement J4 retention exercise");
    }

    public int retainedPayloadCount() {
        throw new UnsupportedOperationException("TODO: implement J4 retention exercise");
    }

    public long retainedBytes() {
        throw new UnsupportedOperationException("TODO: implement J4 retention exercise");
    }
}
