package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Disabled;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Miscellaneous test utilities.
 */
@Disabled
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    /**
     * Constants used in tests.
     */
    public static final String INVALID_STR = "invalid";
    public static final String DIFFERENT_STR = "different";
    public static final String JUST_TESTING_MESSAGE = "just testing...";
    public static final String FOLDER = "my-folder";
    public static final String FILE_NAME = "my-file.txt";


    @SafeVarargs
    public static <T> void assertEqualsHashCode(T instance, T equal, T... diffs) {
        assertThat(instance).isEqualTo(equal).hasSameHashCodeAs(equal);

        for (T diff : diffs) {
            assertThat(instance).isNotEqualTo(diff);
        }

        assertThat(instance).isNotEqualTo(null);
        assertThat(instance).isNotEqualTo(new Object());
    }
}
