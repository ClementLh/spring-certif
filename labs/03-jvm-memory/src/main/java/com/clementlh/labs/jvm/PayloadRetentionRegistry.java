package com.clementlh.labs.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise: model objects kept alive by strong references in a long-lived registry.
 */
public final class PayloadRetentionRegistry {

    private final List<byte[]> retainedPayloads = new ArrayList<>();

    public void retainPayload(int sizeInBytes) {
        if (sizeInBytes <= 0) {
            throw new IllegalArgumentException("Valeur de l'objet négative ou nulle");
        }
        retainedPayloads.add(new byte[sizeInBytes]);
    }

    public void releaseAll() {
        retainedPayloads.clear();
    }

    public int retainedPayloadCount() {
        return retainedPayloads.size();
    }

    public long retainedBytes() {
        long total = 0;
        for (byte[] payload : retainedPayloads) {
            total += payload.length;
        }
        return total;
    }
}
