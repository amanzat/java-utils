package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Miscellaneous {@link Object} utility methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtils {

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
