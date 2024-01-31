package io.github.amanzat.util;

import org.junit.jupiter.api.Test;

import static io.github.amanzat.util.TestUtils.INVALID_STR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ObjectUtilsTest {

    @Test
    void uncheckedCast() {
        Object value = 5L;
        Long asLong = ObjectUtils.uncheckedCast(value);

        assertThat(asLong).isEqualTo(value);
    }

    @Test
    void uncheckedCastInvalid() {
        assertThatExceptionOfType(ClassCastException.class).isThrownBy(() -> {
            @SuppressWarnings("unused")
            Integer result = ObjectUtils.<Integer>uncheckedCast(INVALID_STR);
        });
    }
}