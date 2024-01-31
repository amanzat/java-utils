package io.github.amanzat.util;

import org.junit.jupiter.api.Test;

import static io.github.amanzat.util.TestUtils.JUST_TESTING_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExceptionUtilsTest {

    @Test
    void throwableOfTypeFound() {
        NullPointerException nullPointerException = new NullPointerException();
        RuntimeException runtimeException = new RuntimeException(JUST_TESTING_MESSAGE, nullPointerException);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(runtimeException);

        assertThat(ExceptionUtils.throwableOfType(runtimeException, RuntimeException.class)).isEqualTo(runtimeException);
        assertThat(ExceptionUtils.throwableOfType(illegalArgumentException, NullPointerException.class)).isEqualTo(nullPointerException);
    }

    @Test
    void throwableOfTypeNotFound() {
        NullPointerException nullPointerException = new NullPointerException();
        RuntimeException runtimeException = new RuntimeException(JUST_TESTING_MESSAGE, nullPointerException);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(runtimeException);

        assertThat(ExceptionUtils.throwableOfType(illegalArgumentException, IllegalStateException.class)).isNull();
    }

    @Test
    void throwableOfTypeNullParameters() {
        assertThat((Throwable) ExceptionUtils.throwableOfType(null, null)).isNull();
        assertThat(ExceptionUtils.throwableOfType(null, RuntimeException.class)).isNull();
        assertThat((Throwable) ExceptionUtils.throwableOfType(new RuntimeException(JUST_TESTING_MESSAGE), null)).isNull();
    }

    @Test
    void getCause() {
        Throwable throwable = mock(Throwable.class);

        // no cause
        when(throwable.getCause()).thenReturn(null);
        assertThat(ExceptionUtils.getCause(throwable)).isNull();

        // same instance is the cause
        when(throwable.getCause()).thenReturn(throwable);
        assertThat(ExceptionUtils.getCause(throwable)).isNull();

        Throwable cause = mock(Throwable.class);
        when(throwable.getCause()).thenReturn(cause);
        assertThat(ExceptionUtils.getCause(throwable)).isEqualTo(cause);
    }
}