package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Miscellaneous file related utilities.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtils {

    /**
     * The Unix path separator.
     */
    public static final char UNIX_SEPARATOR = '/';

    /**
     * The Windows path separator.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * Reads all lines from the file at the given file path.
     *
     * @param filePath The file path
     * @return A list with the file lines.
     * @throws IOException if an I/O error occurs
     */
    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path);
    }

    /**
     * Ensures that the directory at the specified path exists.
     *
     * @param path The directory path
     * @return {@code true} if the directory exists, {@code false} otherwise.
     */
    public static boolean ensureDirectoryExists(String path) {
        if (!new File(path).exists()) {
            try {
                Path directoryPath = Files.createDirectories(Paths.get(path));
                logger.info("Directory {} created", path);
                return Files.exists(directoryPath);
            } catch (IOException e) {
                logger.error("The directory {} can't be created", path, e);
                return false;
            }
        }
        return true;
    }

    /**
     * Deletes the file at the specified path if exists.
     *
     * @param filePath The file path
     * @return {@code true} if the file was deleted, {@code false} otherwise.
     */
    public static boolean deleteFile(String filePath) {
        try {
            if (Files.deleteIfExists(Paths.get(filePath))) {
                logger.info("File {} deleted successfully.", filePath);
                return true;
            } else {
                logger.warn("File {} doesn't exists.", filePath);
            }
        } catch (IOException e) {
            logger.error("Error while deleting file {}", filePath, e);
        }
        return false;
    }

    /**
     * Returns the file name from the specified path by eliminating the directories or {@code null} if the path is {@code null}.
     *
     * @param path The path
     * @return The file name from the specified path by eliminating the directories or {@code null} if the path is {@code null}.
     */
    public static String getFileName(String path) {
        if (path == null) {
            return null;
        }

        int index = indexOfLastDirectorySeparator(path);
        return path.substring(index + 1);
    }

    /**
     * Returns the index of the last directory separator character of the path or -1 if there is no such character.
     *
     * @param path The path
     * @return The index of the last directory separator character of the path or -1 if there is no such character.
     */
    public static int indexOfLastDirectorySeparator(String path) {
        if (path == null) {
            return -1;
        }
        int lastUnixPos = path.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPos = path.lastIndexOf(WINDOWS_SEPARATOR);
        return Math.max(lastUnixPos, lastWindowsPos);
    }
}
