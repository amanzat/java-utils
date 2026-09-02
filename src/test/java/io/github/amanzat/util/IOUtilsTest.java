package io.github.amanzat.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.fail;

class IOUtilsTest {

    private static String longText;

    @BeforeAll
    static void beforeAll() throws IOException, URISyntaxException {
        URL resource = IOUtilsTest.class.getClassLoader().getResource("lorem-ipsum.txt");
        if (resource == null) {
            fail("Can't read the resource file.");
        }
        Path path = Paths.get(resource.toURI());
        longText = Files.readString(path);
    }

    @Test
    void toInputStreamNullInput() {
        try (InputStream inputStream = IOUtils.toInputStream(null, null);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            assertThat(IOUtils.copy(inputStream, outputStream)).isZero();
        } catch (IOException e) {
            fail("Unexpected exception", e);
        }
    }

    @Test
    @Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void copyInvalidBufferSize() {
        try (InputStream inputStream = IOUtils.toInputStream(longText, StandardCharsets.UTF_8);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> IOUtils.copy(inputStream, outputStream, 0));
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> IOUtils.copy(inputStream, outputStream, -1));
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> IOUtils.copy(inputStream, outputStream, new byte[0]));
        } catch (IOException e) {
            fail("Unexpected exception", e);
        }
    }

    @Test
    void copy() {
        try (InputStream inputStream = IOUtils.toInputStream(longText, StandardCharsets.UTF_8);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            long count = IOUtils.copy(inputStream, outputStream);
            assertThat(count).isGreaterThan(1000).isEqualTo(longText.getBytes().length);
            assertThat(outputStream).hasToString(longText);
        } catch (IOException e) {
            fail("Unexpected exception", e);
        }
    }
}