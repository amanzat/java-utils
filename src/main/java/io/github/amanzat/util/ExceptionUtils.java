package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Miscellaneous {@link Throwable} utility methods and constants.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionUtils {

    /**
     * Returns the first {@link Throwable throwable} of the specified type in the exception chain if exists, {@code null} otherwise.
     *
     * @param throwable The {@link Throwable} to inspect
     * @param type      The type to search for
     * @param <T>       The searched throwable type
     * @return The first {@link Throwable throwable} of the specified type in the exception chain if exists, {@code null} otherwise.
     */
    public static <T extends Throwable> T throwableOfType(Throwable throwable, Class<T> type) {
        if (throwable == null || type == null) {
            return null;
        }

        // keep track of throwables in the chain to avoid infinite recursion
        Set<Throwable> throwables = new HashSet<>();
        while (throwable != null && throwables.add(throwable)) {
            if (type.isAssignableFrom(throwable.getClass())) {
                // found
                return type.cast(throwable);
            }
            throwable = throwable.getCause();
        }
        // not found
        return null;
    }

    /**
     * Returns the cause of this {@link Throwable throwable} if exists and is not the same instance with the throwable.
     *
     * @param throwable The throwable
     * @return The cause of this {@link Throwable throwable}.
     */
    public static Throwable getCause(Throwable throwable) {
        Throwable cause = throwable.getCause();
        return cause != null && cause != throwable ? cause : null;
    }
}
