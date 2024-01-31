package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Miscellaneous collections related utilities.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtils {

    /**
     * Returns an optional containing the first element of the list if any.
     *
     * @param list The list
     * @param <E>  The list elements type.
     * @return An optional containing the first element of the list if any
     * @implNote The optional will be empty if the list is empty or if the fist element is {@code null}.
     */
    public static <E> Optional<E> getFirst(List<E> list) {
        return list == null || list.isEmpty() ? Optional.empty() : Optional.ofNullable(list.get(0));
    }

    /**
     * Returns {@code true} if the specified collection is empty, {@code false} otherwise.
     *
     * @param collection The collection to check
     * @return {@code true} if the specified collection is empty, {@code false} otherwise.
     * @implNote A {@code null} collection is considered empty.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Transforms an iterator into a stream of lists with a maximum size.
     *
     * @param iterator The iterator
     * @param size     The list (chunk) max size
     * @param <T>      The element type.
     * @return A stream of lists with the specified maximum size.
     * @see <a href=https://stackoverflow.com/questions/27583623/is-there-an-elegant-way-to-process-a-stream-in-chunks>url</a>
     */
    public static <T> Stream<List<T>> chunkify(Iterator<T> iterator, int size) {
        Iterator<List<T>> listIterator = new Iterator<>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public List<T> next() {
                List<T> result = new ArrayList<>(size);
                for (int i = 0; i < size && iterator.hasNext(); i++) {
                    result.add(iterator.next());
                }
                return result;
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(listIterator, Spliterator.ORDERED), false);
    }
}
