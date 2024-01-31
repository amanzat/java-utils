package io.github.amanzat.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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