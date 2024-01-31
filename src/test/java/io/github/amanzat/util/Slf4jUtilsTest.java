package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class Slf4jUtilsTest {

    @Test
    void log() {
        Logger logger = mock(Logger.class);
        String format = "test format {}";
        Object[] args = new Object[0];

        Slf4jUtils.log(logger, Level.TRACE, format, args);
        verify(logger).trace(format, args);

        Slf4jUtils.log(logger, Level.DEBUG, format, args);
        verify(logger).debug(format, args);

        Slf4jUtils.log(logger, Level.INFO, format, args);
        verify(logger).info(format, args);

        Slf4jUtils.log(logger, Level.WARN, format, args);
        verify(logger).warn(format, args);

        Slf4jUtils.log(logger, Level.ERROR, format, args);
        verify(logger).error(format, args);
    }
}