package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.github.amanzat.util.StringUtils.*;
import static io.github.amanzat.util.TestUtils.ABC_STRING;
import static io.github.amanzat.util.TestUtils.DUMMY_STRING;
import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @Test
    void isEmpty() {
        assertThat(StringUtils.isEmpty(EMPTY_STRING)).isTrue();
        assertThat(StringUtils.isEmpty(null)).isTrue();

        assertThat(StringUtils.isEmpty(DUMMY_STRING)).isFalse();
        assertThat(StringUtils.isEmpty("   ")).isFalse();
    }

    @Test
    void length() {
        assertThat(StringUtils.length(EMPTY_STRING)).isZero();
        assertThat(StringUtils.length(null)).isZero();

        assertThat(StringUtils.length("   ")).isEqualTo(3);
        assertThat(StringUtils.length(DUMMY_STRING)).isEqualTo(DUMMY_STRING.length());
    }

    @Test
    void isBlank() {
        assertThat(StringUtils.isBlank(EMPTY_STRING)).isTrue();
        assertThat(StringUtils.isBlank("   ")).isTrue();
        assertThat(StringUtils.isBlank("    \t")).isTrue();
        assertThat(StringUtils.isBlank("    \n  ")).isTrue();
        assertThat(StringUtils.isBlank(null)).isTrue();

        assertThat(StringUtils.isBlank(DUMMY_STRING)).isFalse();
        assertThat(StringUtils.isBlank("   a   ")).isFalse();
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void toUpperCase() {
        assertThat(StringUtils.toUpperCase(null)).isNull();

        assertThat(StringUtils.toUpperCase("AB")).isEqualTo("AB");
        assertThat(StringUtils.toUpperCase("abcd")).isEqualTo("ABCD");
        assertThat(StringUtils.toUpperCase("a1b2")).isEqualTo("A1B2");
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    void toLowerCase() {
        assertThat(StringUtils.toLowerCase(null)).isNull();

        assertThat(StringUtils.toLowerCase("ct")).isEqualTo("ct");
        assertThat(StringUtils.toLowerCase("ABCD")).isEqualTo("abcd");
        assertThat(StringUtils.toLowerCase("C1t2")).isEqualTo("c1t2");
    }

    @Test
    void truncateWithMarker() {
        assertThat(StringUtils.truncateWithMarker(null, 4)).isNull();
        assertThat(StringUtils.truncateWithMarker(ABC_STRING, 4)).isEqualTo(ABC_STRING);
        assertThat(StringUtils.truncateWithMarker(ABC_STRING, 3)).isEqualTo(ABC_STRING);
        assertThat(StringUtils.truncateWithMarker("123", 4)).isEqualTo("123");

        assertThat(StringUtils.truncateWithMarker("ab", 1)).isEqualTo("a");
        assertThat(StringUtils.truncateWithMarker(ABC_STRING, 2)).isEqualTo("ab");
        assertThat(StringUtils.truncateWithMarker("123", 2)).isEqualTo("12");

        assertThat(StringUtils.truncateWithMarker("abcdef", 4)).isEqualTo("ab..");
        assertThat(StringUtils.truncateWithMarker("1234567890", 9)).isEqualTo("1234567..");
    }

    @Test
    void leftPad() {
        assertThat(StringUtils.leftPad("123", 5, '0')).isEqualTo("00123");
        assertThat(StringUtils.leftPad(EMPTY_STRING, 4, '*')).isEqualTo("****");

        assertThat(StringUtils.leftPad(null, 10, '*')).isNull();
        assertThat(StringUtils.leftPad(EMPTY_STRING, 0, '*')).isEqualTo(EMPTY_STRING);
        assertThat(StringUtils.leftPad(EMPTY_STRING, -1, '*')).isEqualTo(EMPTY_STRING);
        assertThat(StringUtils.leftPad(DUMMY_STRING, 0, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.leftPad(DUMMY_STRING, -1, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.leftPad(DUMMY_STRING, DUMMY_STRING.length() - 1, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.leftPad(ABC_STRING, 3, '*')).isEqualTo(ABC_STRING);
    }

    @Test
    void rightPad() {
        assertThat(StringUtils.rightPad("123", 5, '0')).isEqualTo("12300");
        assertThat(StringUtils.rightPad(EMPTY_STRING, 4, '*')).isEqualTo("****");

        assertThat(StringUtils.rightPad(null, 10, '*')).isNull();
        assertThat(StringUtils.rightPad(EMPTY_STRING, 0, '*')).isEqualTo(EMPTY_STRING);
        assertThat(StringUtils.rightPad(EMPTY_STRING, -1, '*')).isEqualTo(EMPTY_STRING);
        assertThat(StringUtils.rightPad(DUMMY_STRING, 0, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.rightPad(DUMMY_STRING, -1, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.rightPad(DUMMY_STRING, DUMMY_STRING.length() - 1, '*')).isEqualTo(DUMMY_STRING);
        assertThat(StringUtils.rightPad(ABC_STRING, 3, '*')).isEqualTo(ABC_STRING);
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
            "1234,4",
            "12345,5",
            "'',0"
    })
    void onlyDigits(String value, int length) {
        assertThat(StringUtils.onlyDigits(value, length)).isTrue();
    }

    @ParameterizedTest
    @CsvSource({
            "1234,5",
            "1234a,5",
            "test,4",
            ",0"
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