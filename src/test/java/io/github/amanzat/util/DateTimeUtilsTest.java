package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DateTimeUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "0, 00:00:00.000",
            "3600000, 01:00:00.000",
            "60000, 00:01:00.000",
            "1000, 00:00:01.000",
            "1000000123, 277:46:40.123"
    })
    void formatDurationHMS(long millis, String expected) {
        assertThat(DateTimeUtils.formatDurationHMS(millis)).isEqualTo(expected);
    }

    @Test
    void formatDurationNegativeMillis() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> DateTimeUtils.formatDurationHMS(-1L));
    }

    @Test
    void asDuration() {
        assertThat(DateTimeUtils.asDuration("PT1S")).isEqualTo(Duration.ofSeconds(1));
        assertThat(DateTimeUtils.asDuration("PT5M")).isEqualTo(Duration.ofMinutes(5));
        assertThat(DateTimeUtils.asDuration("PT2H")).isEqualTo(Duration.ofHours(2));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "      ", "invalid", "abc", "123"})
    void asDurationBlankOrInvalid(String value) {
        assertThat(DateTimeUtils.asDuration(value)).isNull();
    }
}