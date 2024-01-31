package io.github.amanzat.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Miscellaneous {@link Thread} utility methods.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadUtils {

    /**
     * Safely sleeps the current thread for the specified number of millis.
     *
     * @param millis The number of millis
     */
    public static void safeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.warn("Error while sleeping.", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Safely waits for the latch to count down to zero.
     *
     * @param latch The latch
     */
    public static void safeAwait(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.warn("Error occurred while awaiting for CountDownLatch.", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Safely acquires a semaphore permit.
     *
     * @param semaphore The semaphore
     */
    public static void safeAcquire(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            logger.warn("Error occurred while acquiring semaphore permit.", e);
            Thread.currentThread().interrupt();
        }
    }
}
