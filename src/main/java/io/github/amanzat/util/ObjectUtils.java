package io.github.amanzat.util;

/**
 * Miscellaneous {@link Object} utility methods.
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    /**
     * Performs an unchecked cast for the specified object.
     *
     * @param value The object
     * @param <T>   The expected type.
     * @return The object cast to the expected type.
     * @throws ClassCastException if the object can't be cast
     */
    @SuppressWarnings("unchecked")
    public static <T> T uncheckedCast(Object value) {
        return (T) value;
    }
}
