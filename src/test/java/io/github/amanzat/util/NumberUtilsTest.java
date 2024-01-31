package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static io.github.amanzat.util.TestUtils.INVALID_STR;
import static org.assertj.core.api.Assertions.assertThat;

class NumberUtilsTest {

    @Test
    void safeParseDecimal() {
        String input = "123.45";
        assertThat(NumberUtils.safeParseDecimal(input)).isEqualTo(new BigDecimal(input));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  ", "      ", "A", "b", "!", INVALID_STR})
    void safeParseDecimal(String input) {
        assertThat(NumberUtils.safeParseDecimal(input)).isNull();
    }
}