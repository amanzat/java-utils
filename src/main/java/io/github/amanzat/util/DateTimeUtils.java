package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * Miscellaneous date and time utility methods and constants.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

    /**
     * The number of milliseconds in an hour.
     */
    public static final long HOUR_MILLIS = Duration.ofHours(1).toMillis();

    /**
     * The number of milliseconds in a minute.
     */
    public static final long MINUTE_MILLIS = Duration.ofMinutes(1).toMillis();

    /**
     * The number of milliseconds in a second.
     */
    public static final long SECOND_MILLIS = Duration.ofSeconds(1).toMillis();

    /**
     * Formats the time duration in millis using the {@code HH:mm:ss.SSS} format.
     *
     * @param millis The time duration in millis
     * @return The formatted time duration.
     */
    public static String formatDurationHMS(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("The time duration in millis can't be negative.");
        }
        long hours = Math.floorDiv(millis, HOUR_MILLIS);
        millis = millis % HOUR_MILLIS;
        long minutes = Math.floorDiv(millis, MINUTE_MILLIS);
        millis = millis % MINUTE_MILLIS;
        long seconds = Math.floorDiv(millis, SECOND_MILLIS);
        millis = millis % SECOND_MILLIS;
        return StringUtils.leftPad(String.valueOf(hours), 2, '0') + ':' +
                StringUtils.leftPad(String.valueOf(minutes), 2, '0') + ':' +
                StringUtils.leftPad(String.valueOf(seconds), 2, '0') + '.' +
                StringUtils.leftPad(String.valueOf(millis), 3, '0');
    }

    /**
     * Parses the specified string value to a {@link Duration} if possible, otherwise returns {@code null}.
     *
     * @param value The string value
     * @return The corresponding duration or {@code null}.
     */
    public static Duration asDuration(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        try {
            return Duration.parse(value);
        } catch (DateTimeParseException e) {
            logger.warn("Error parsing {} to Duration.", value);
            return null;
        }
    }
}
