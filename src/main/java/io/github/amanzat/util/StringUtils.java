package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * Miscellaneous {@link String} utility methods and constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

    /**
     * The empty {@link String} constant.
     */
    public static final String EMPTY_STRING = "";

    /**
     * The new line {@link String}.
     */
    public static final String NEW_LINE = System.lineSeparator();

    /**
     * The truncation marker {@link String}.
     */
    public static final String TRUNCATION_MARKER = "..";

    /**
     * Returns {@code true} if the {@link CharSequence} is {@code null} or empty, {@code false} otherwise.
     *
     * @param charSequence The {@link CharSequence}
     * @return {@code true} if the {@link CharSequence} is {@code null} or empty, {@code false} otherwise.
     */
    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.isEmpty();
    }

    /**
     * Returns the length of the {@link CharSequence} or 0 if the value is {@code null}.
     *
     * @param charSequence The {@link CharSequence}
     * @return The length of the {@link CharSequence} or 0 if the value is {@code null}.
     */
    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    /**
     * Checks if a CharSequence is empty, {@code null} or whitespace only.
     * <br>Whitespace is defined by {@link Character#isWhitespace(char)}.
     *
     * @param charSequence The {@link CharSequence}, may be {@code null}
     * @return {@code true} if the CharSequence is {@code null}, empty or whitespace only, {@code false} otherwise.
     */
    public static boolean isBlank(CharSequence charSequence) {
        int length = length(charSequence);
        if (length == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Safely converts the specified {@link String value} to upper-case handling {@code nulls}.
     *
     * @param value The string value
     * @return The string to uppercase or {@code null}.
     */
    public static String toUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    /**
     * Safely converts the specified {@link String value} to lower-case handling {@code nulls}.
     *
     * @param value The string value
     * @return The string to lowercase or {@code null}.
     */
    public static String toLowerCase(String value) {
        return value != null ? value.toLowerCase() : null;
    }

    /**
     * Truncates the specified {@link String} value to the maximum length and add the truncation marker
     * if the max length is greater than the marker's length.
     * <br>If the string length is less or equal with the maximum length, no truncation is performed.
     *
     * @param value     The {@link String} value to truncate
     * @param maxLength The maximum length
     * @return The truncated {@link String value} or the original {@link String}.
     */
    public static String truncateWithMarker(String value, int maxLength) {
        if (isBlank(value) || value.length() <= maxLength) {
            return value;
        }

        int markerLength = TRUNCATION_MARKER.length();
        return maxLength <= markerLength
                ? value.substring(0, maxLength)
                : value.substring(0, maxLength - markerLength).concat(TRUNCATION_MARKER);
    }

    /**
     * Left pads the specified string input with the given character.
     *
     * @param input     The {@link String} input to pad
     * @param size      The size to pad
     * @param character The character to pad with
     * @return The left padded String result.
     */
    public static String leftPad(String input, int size, char character) {
        return pad(input, size, character, Padding.LEFT);
    }

    /**
     * Right pads the specified string input with the given character.
     *
     * @param input     The {@link String} input to pad
     * @param size      The size to pad
     * @param character The character to pad with
     * @return The right padded String result.
     */
    public static String rightPad(String input, int size, char character) {
        return pad(input, size, character, Padding.RIGHT);
    }

    /**
     * Concatenates a string with an array of strings using a delimiter and returns the resulting string.
     *
     * @param value     The string value
     * @param delimiter The delimiter
     * @param others    The other strings
     * @return The resulting string.
     */
    public static String concat(String value, char delimiter, String... others) {
        if (others == null || others.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder(value);
        for (String other : others) {
            result.append(delimiter).append(other);
        }
        return result.toString();
    }

    /**
     * Concatenates an array of strings using a delimiter and returns the resulting string.
     *
     * @param delimiter The delimiter
     * @param values    The array of strings
     * @return The resulting string.
     */
    public static String concat(char delimiter, String... values) {
        if (values == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (first) {
                first = false;
            } else {
                result.append(delimiter);
            }
            result.append(value);
        }
        return result.toString();
    }

    /**
     * Concatenates a string with an array of strings and returns the resulting array.
     *
     * @param value  The string value
     * @param others The other strings
     * @return The resulting array of strings.
     */
    public static String[] concat(String value, String... others) {
        if (others == null || others.length == 0) {
            return new String[]{value};
        }
        String[] result = new String[others.length + 1];
        result[0] = value;
        System.arraycopy(others, 0, result, 1, others.length);
        return result;
    }

    /**
     * Checks if the specified {@link CharSequence} has only digits and the specified length.
     *
     * @param value  The {@link CharSequence}
     * @param length The expected length
     * @return {@code true} if the value has the expected length and has only digits, {@code false} otherwise.
     */
    public static boolean onlyDigits(CharSequence value, int length) {
        if (value == null) {
            return false;
        }
        if (value.length() != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pads the specified string input with the given character.
     *
     * @param input     The {@link String} input to pad
     * @param size      The size to pad
     * @param character The character to pad with
     * @param padding   The padding type
     * @return The padded String result.
     */
    private static String pad(String input, int size, char character, Padding padding) {
        if (input == null) {
            return null;
        }

        int padSize = size - input.length();
        if (padSize <= 0) {
            // no need to pad
            return input;
        }
        char[] chars = new char[padSize];
        Arrays.fill(chars, character);
        return padding.concat(input, String.valueOf(chars));
    }

    /**
     * Defines the padding types.
     */
    public enum Padding {

        /**
         * The left padding enum.
         */
        LEFT {
            @Override
            public String concat(String str, String padStr) {
                return padStr.concat(str);
            }
        },
        /**
         * The right padding enum.
         */
        RIGHT {
            @Override
            public String concat(String str, String padStr) {
                return str.concat(padStr);
            }
        };

        /**
         * Concatenates the str with the specified pad string.
         *
         * @param str    The string to pad
         * @param padStr The pad string
         * @return The concatenated result.
         */
        public abstract String concat(String str, String padStr);
    }
}
