package io.github.amanzat.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ComparableUtilsTest {

    @Test
    void safeCompare() {
        assertThat(ComparableUtils.safeCompare(null, 1)).isNegative();
        assertThat(ComparableUtils.safeCompare(1, null)).isPositive();
        assertThat(ComparableUtils.safeCompare(1, 1)).isZero();
        assertThat(ComparableUtils.safeCompare(null, null)).isZero();
    }
}