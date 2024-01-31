package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Miscellaneous {@link Number} utility methods and constants.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtils {

    /**
     * Safely parses the specified string value to a {@link BigDecimal} ignoring nulls and blank strings.
     *
     * @param strValue The string value to parse
     * @return The corresponding {@link BigDecimal} if the string value is valid, {@code null} otherwise.
     */
    public static BigDecimal safeParseDecimal(String strValue) {
        if (StringUtils.isBlank(strValue)) {
            return null;
        }

        try {
            return new BigDecimal(strValue);
        } catch (NumberFormatException e) {
            logger.warn("Can't parse BigDecimal from string value " + strValue);
            return null;
        }
    }
}
