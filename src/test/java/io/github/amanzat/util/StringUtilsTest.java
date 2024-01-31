package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.amanzat.util.StringUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "'', true",
            ", true",
            "a string, false",
            "'    ', false"
    })
    void isEmpty(String str, boolean expected) {
        assertThat(StringUtils.isEmpty(str)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'', 0",
            ", 0",
            "a string, 8",
            "'   ', 3"
    })
    void length(String str, int expected) {
        assertThat(StringUtils.length(str)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'', true",
            ", true",
            "'     ', true",
            "'     \t', true",
            "'     \n   ', true",
            "dummy string, false",
            "'  a ', false"
    })
    void isBlank(String str, boolean expected) {
        assertThat(StringUtils.isBlank(str)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "'  ', '  '",
            "AB, AB",
            "abcd, ABCD",
            "a1b2, A1B2"
    })
    void toUpperCase(String str, String expected) {
        assertThat(StringUtils.toUpperCase(str)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "'  ', '  '",
            "ab, ab",
            "ABCD, abcd",
            "C1D2, c1d2"
    })
    void toLowerCase(String str, String expected) {
        assertThat(StringUtils.toLowerCase(str)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            ", 4,",
            "abc, 4, abc",
            "abc, 3, abc",
            "123, 3, 123",
            "ab, 1, a",
            "abc, 2, ab",
            "abcdef, 4, ab..",
            "1234567890, 9, 1234567.."
    })
    void truncateWithMarker(String str, int maxLength, String expected) {
        assertThat(StringUtils.truncateWithMarker(str, maxLength)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'', 4, *, ****",
            "123, 5, 0, 00123",
            "abc, 6, -, ---abc",
            ", 10, *,",
            "'', 0, *, ''",
            "'', -1, *, ''",
            "my string, 0, *, my string",
            "my other string, -1, *, my other string",
            "a string, 7, *, a string",
            "abc, 3, *, abc",
            ", 10,*,",
    })
    void leftPad(String str, int size, char character, String expected) {
        assertThat(StringUtils.leftPad(str, size, character)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "'', 4, *, ****",
            "123, 5, 0, 12300",
            "abc, 6, -, abc---",
            ", 10, *,",
            "'', 0, *, ''",
            "'', -1, *, ''",
            "my string, 0, *, my string",
            "my other string, -1, *, my other string",
            "a string, 7, *, a string",
            "abc, 3, *, abc",
            ", 10,*,",
    })
    void rightPad(String str, int size, char character, String expected) {
        assertThat(StringUtils.rightPad(str, size, character)).isEqualTo(expected);
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void concatAsString() {
        String result = StringUtils.concat(null, ' ', (String[]) null);
        assertThat(result).isNull();

        String value = "my value";
        result = StringUtils.concat(value, ' ', (String[]) null);
        assertThat(result).isEqualTo(value);

        result = StringUtils.concat(value, ' ');
        assertThat(result).isEqualTo(value);

        result = StringUtils.concat(value, ' ', "x", "y", "z");
        assertThat(result).isEqualTo(value + " x y z");
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void concatArrayAsString() {
        String result = StringUtils.concat(' ', (String[]) null);
        assertThat(result).isNull();

        result = StringUtils.concat(' ');
        assertThat(result).isEqualTo(EMPTY_STRING);

        result = StringUtils.concat(' ', "x", "y", "z");
        assertThat(result).isEqualTo("x y z");
    }

    @Test
    void concatAsArray() {
        String[] result = StringUtils.concat(null, (String[]) null);
        assertThat(result).isEqualTo(new String[]{null});

        String value = "value";
        result = StringUtils.concat(value, (String[]) null);
        assertThat(result).isEqualTo(new String[]{value});

        result = StringUtils.concat(value);
        assertThat(result).isEqualTo(new String[]{value});

        result = StringUtils.concat(value, "a", "b", "c", "d");
        assertThat(result).isEqualTo(new String[]{value, "a", "b", "c", "d"});
    }

    @ParameterizedTest
    @CsvSource({
            "1234, 4",
            "12345, 5",
            "'', 0"
    })
    void onlyDigits(String value, int length) {
        assertThat(StringUtils.onlyDigits(value, length)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1234, 5",
            "1234a, 5",
            "test, 4",
            ", 0"
    })
    void notOnlyDigitsOrNull(String value, int length) {
        assertThat(StringUtils.onlyDigits(value, length)).isFalse();
    }

    @Test
    void constants() {
        assertThat(EMPTY_STRING).isEmpty();
        assertThat(TRUNCATION_MARKER).isEqualTo("..");
        assertThat(System.lineSeparator()).isEqualTo(NEW_LINE);
    }
}