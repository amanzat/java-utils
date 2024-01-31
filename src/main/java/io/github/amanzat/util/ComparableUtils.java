package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Miscellaneous comparable utility methods.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComparableUtils {

    /**
     * Does a safe comparison of two {@link Comparable} objects accounting for nulls.
     *
     * @param first  The first object
     * @param second The second object
     * @param <T>    The comparable type.
     * @return A positive number if the first object is larger, a negative number if the second
     * object is larger, or 0 if they are equal. Null is considered less than any non-null value.
     */
    public static <T extends Comparable<T>> int safeCompare(T first, T second) {
        if (first != null && second != null) {
            return first.compareTo(second);
        } else if (first == null && second != null) {
            return -1;
        } else if (first != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
