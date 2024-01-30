package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static io.github.amanzat.util.TestUtils.FILE_NAME;
import static io.github.amanzat.util.TestUtils.FOLDER;
import static org.assertj.core.api.Assertions.assertThat;

class FileUtilsTest {

    @Test
    void readAllLines(@TempDir Path tempDir) throws IOException {
        String line1 = "line1";
        String line2 = "line2";
        String line3 = "line3";
        String fileContent = line1 + System.lineSeparator() + line2 + System.lineSeparator() + line3;
        Path file = tempDir.resolve("file.txt");
        Files.writeString(file, fileContent);

        String filePath = file.toAbsolutePath().toString();
        List<String> lines = FileUtils.readAllLines(filePath);
        assertThat(lines).isNotNull().hasSize(3).contains(line1, line2, line3);

        Files.delete(file);
    }

    @Test
    void ensureDirectoryExists(@TempDir Path tempDir) {
        assertThat(tempDir).exists();

        Path path = tempDir.resolve(FOLDER);
        assertThat(path).doesNotExist();

        String pathAsString = path.toAbsolutePath().toString();
        // ensure directory exists by creating it
        assertThat(FileUtils.ensureDirectoryExists(pathAsString)).isTrue();
        assertThat(path).exists();

        // the directory already exists
        assertThat(FileUtils.ensureDirectoryExists(pathAsString)).isTrue();
    }

    @Test
    void ensureDirectoryExistsAlready(@TempDir Path tempDir) {
        assertThat(tempDir).exists();

        String pathAsString = tempDir.toAbsolutePath().toString();
        // the directory already exists
        assertThat(FileUtils.ensureDirectoryExists(pathAsString)).isTrue();
    }

    @Test
    void ensureDirectoryExistsInvalidPath(@TempDir Path tempDir) throws IOException {
        assertThat(tempDir).exists();

        Path path = tempDir.resolve(FILE_NAME);
        assertThat(path).doesNotExist();
        Path filePath = Files.createFile(path);
        assertThat(filePath).exists();

        // try to create a directory using a file in path
        path = filePath.resolve(FOLDER);

        String pathAsString = path.toAbsolutePath().toString();
        // the directory can't be created
        assertThat(FileUtils.ensureDirectoryExists(pathAsString)).isFalse();
    }

    @Test
    void deleteFile(@TempDir Path tempDir) throws IOException {
        assertThat(tempDir).exists();

        Path path = tempDir.resolve(FILE_NAME);
        assertThat(path).doesNotExist();
        Path filePath = Files.createFile(path);
        assertThat(filePath).exists();

        assertThat(FileUtils.deleteFile(filePath.toAbsolutePath().toString())).isTrue();
    }

    @Test
    void deleteFileNotExists(@TempDir Path tempDir) {
        assertThat(tempDir).exists();

        Path path = tempDir.resolve(FILE_NAME);
        assertThat(path).doesNotExist();

        assertThat(FileUtils.deleteFile(path.toAbsolutePath().toString())).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "/tmp/data/file1.txt, file1.txt",
            "C:\\tmp\\data\\other\\sample.csv, sample.csv"
    })
    void getFileName(String path, String expectedFileName) {
        assertThat(FileUtils.getFileName(path)).isEqualTo(expectedFileName);
    }

    @ParameterizedTest
    @CsvSource({
            ", -1",
            "/tmp/my-dir/my-file.txt, 11",
            "C:\\tmp\\my-dir\\my-file.txt, 13"
    })
    void indexOfLastDirectorySeparator(String path, int expected) {
        assertThat(FileUtils.indexOfLastDirectorySeparator(path)).isEqualTo(expected);
    }
}