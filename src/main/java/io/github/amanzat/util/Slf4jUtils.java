package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * Miscellaneous utility methods for Slf4J.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Slf4jUtils {

    /**
     * Simple logging based on the specified parameters.
     *
     * @param logger    The logger
     * @param level     The level
     * @param format    The message format
     * @param arguments The arguments
     */
    public static void log(Logger logger, Level level, String format, Object... arguments) {
        switch (level) {
            case TRACE -> logger.trace(format, arguments);
            case DEBUG -> logger.debug(format, arguments);
            case INFO -> logger.info(format, arguments);
            case WARN -> logger.warn(format, arguments);
            case ERROR -> logger.error(format, arguments);
        }
    }
}
