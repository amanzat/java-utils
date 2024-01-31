package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * Miscellaneous I/O streams related utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IOUtils {

    /**
     * The default buffer size ({@value}) to use in copy methods.
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Represents the end-of-file (or stream).
     */
    public static final int EOF = -1;

    /**
     * Copies bytes a {@code InputStream} to an {@code OutputStream}.
     * <p>
     * This method buffers the input internally, using the default buffer size: {@link #DEFAULT_BUFFER_SIZE}.
     *
     * @param inputStream  The {@code InputStream} to read.
     * @param outputStream The {@code OutputStream} to write.
     * @return The number of bytes copied.
     * @throws IOException if an I/O error occurs.
     */
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copies bytes from an {@code InputStream} to an {@code OutputStream} using an internal buffer of the given size.
     *
     * @param inputStream  The {@code InputStream} to read.
     * @param outputStream The {@code OutputStream} to write to
     * @param bufferSize   The bufferSize used to copy from the input to the output
     * @return The number of bytes copied.
     * @throws IOException if an I/O error occurs.
     */
    public static long copy(InputStream inputStream, OutputStream outputStream, int bufferSize) throws IOException {
        return copy(inputStream, outputStream, new byte[bufferSize]);
    }

    /**
     * Copies bytes from a {@code InputStream} to an {@code OutputStream}.
     * <p>
     * This method uses the provided buffer, so there is no need to use a
     * {@code BufferedInputStream}.
     *
     * @param inputStream  The {@code InputStream} to read.
     * @param outputStream The {@code OutputStream} to write.
     * @param buffer       The buffer to use for the copy
     * @return The number of bytes copied.
     * @throws IOException if an I/O error occurs.
     */
    public static long copy(InputStream inputStream, OutputStream outputStream, byte[] buffer) throws IOException {
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long count = 0;
        int n;
        while (EOF != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * Converts the specified string to an input stream, encoded as bytes using the specified character encoding.
     *
     * @param input   The string to convert
     * @param charset The charset to use, {@code null} allowed and transformed to default charset.
     * @return An input stream.
     */
    public static InputStream toInputStream(String input, Charset charset) {
        return new ByteArrayInputStream(input.getBytes(charset == null ? Charset.defaultCharset() : charset));
    }
}
