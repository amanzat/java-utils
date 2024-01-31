package io.github.amanzat.util;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These tests are time-consuming as they test timeouts and thread synchronization
 * and should be disabled if they become flaky.
 */
class ThreadUtilsTest {

    @Test
    void safeSleep() {
        assertDoesNotThrow(() -> ThreadUtils.safeSleep(1));
    }

    @Test
    void safeSleepWithTimeout() {
        // expect assertion error due to timeout lower than the sleep amount
        assertThrows(AssertionFailedError.class, () -> assertTimeoutPreemptively(Duration.ofMillis(10), () -> ThreadUtils.safeSleep(1000)));
    }

    @Test
    void safeAwait() {
        int count = 10;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            CompletableFuture.runAsync(latch::countDown);
        }

        ThreadUtils.safeAwait(latch);
        assertEquals(0L, latch.getCount());
    }

    @Test
    void safeAwaitWithTimeout() {
        // expect assertion error due to timeout as the countdown latch is never decreased
        assertThrows(AssertionFailedError.class, () -> assertTimeoutPreemptively(Duration.ofMillis(10), () -> ThreadUtils.safeAwait(new CountDownLatch(1))));
    }

    @Test
    void safeAcquire() {
        Semaphore semaphore = new Semaphore(1);

        ThreadUtils.safeAcquire(semaphore);
        assertEquals(0L, semaphore.availablePermits());
    }

    @Test
    void safeAcquireTimeout() {
        // expect assertion error due to timeout as the semaphore has no permits
        assertThrows(AssertionFailedError.class, () -> assertTimeoutPreemptively(Duration.ofMillis(10), () -> ThreadUtils.safeAcquire(new Semaphore(0))));
    }
}