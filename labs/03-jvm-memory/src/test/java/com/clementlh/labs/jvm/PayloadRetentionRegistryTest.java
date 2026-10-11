package com.clementlh.labs.jvm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PayloadRetentionRegistryTest {

    private final PayloadRetentionRegistry registry = new PayloadRetentionRegistry();

    @Test
    void should_retain_payloads_and_report_their_total_size() {
        registry.retainPayload(1_024);
        registry.retainPayload(2_048);
        registry.retainPayload(4_096);

        assertThat(registry.retainedPayloadCount()).isEqualTo(3);
        assertThat(registry.retainedBytes()).isEqualTo(7_168L);
    }

    @Test
    void should_release_all_payload_references() {
        registry.retainPayload(1_024);
        registry.retainPayload(2_048);

        registry.releaseAll();

        assertThat(registry.retainedPayloadCount()).isZero();
        assertThat(registry.retainedBytes()).isZero();
    }

    @Test
    void should_accept_new_payloads_after_release() {
        registry.retainPayload(1_024);
        registry.releaseAll();

        registry.retainPayload(512);

        assertThat(registry.retainedPayloadCount()).isEqualTo(1);
        assertThat(registry.retainedBytes()).isEqualTo(512L);
    }

    @Test
    void should_reject_non_positive_payload_sizes() {
        assertThatIllegalArgumentException().isThrownBy(() -> registry.retainPayload(0));
        assertThatIllegalArgumentException().isThrownBy(() -> registry.retainPayload(-1));
        assertThat(registry.retainedPayloadCount()).isZero();
        assertThat(registry.retainedBytes()).isZero();
    }
}
