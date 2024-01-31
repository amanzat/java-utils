package io.github.amanzat.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.github.amanzat.util.StringUtils.EMPTY_STRING;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

    @Test
    void getFirst() {
        assertThat(CollectionUtils.getFirst(null)).isEmpty();
        assertThat(CollectionUtils.getFirst(Collections.emptyList())).isEmpty();
        assertThat(CollectionUtils.getFirst(Collections.singletonList(null))).isEmpty();
        assertThat(CollectionUtils.getFirst(Collections.singletonList(123))).isPresent().contains(123);
        assertThat(CollectionUtils.getFirst(Arrays.asList("a", "b", "c"))).isPresent().contains("a");
    }

    @Test
    @SuppressWarnings("ConstantValue")
    void isEmpty() {
        assertThat(CollectionUtils.isEmpty(null)).isTrue();
        assertThat(CollectionUtils.isEmpty(Collections.emptyList())).isTrue();

        assertThat(CollectionUtils.isEmpty(Collections.singletonList(null))).isFalse();
        assertThat(CollectionUtils.isEmpty(Collections.singletonList(123))).isFalse();
        assertThat(CollectionUtils.isEmpty(Arrays.asList("a", "b", "c"))).isFalse();
    }

    @Test
    void chunkify() {
        List<String> strings = Collections.nCopies(150, EMPTY_STRING);

        assertThat(CollectionUtils.chunkify(strings.listIterator(), 10)).hasSize(15);
        assertThat(CollectionUtils.chunkify(strings.listIterator(), 20)).hasSize(8);
    }

    @Test
    void chunkifyEmpty() {
        assertThat(CollectionUtils.chunkify(Collections.emptyIterator(), 5)).isEmpty();
    }
}