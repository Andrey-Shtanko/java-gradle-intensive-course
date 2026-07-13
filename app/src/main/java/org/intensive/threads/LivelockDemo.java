package org.intensive.threads;

/**
 * Throwaway demo: two CROSSING transfers (A->B and B->A) that livelock
 * against the current BankAccount implementation (sleep(1000) held inside
 * the lock + tryLock-based refund loop).
 *
 * Expected: you will see repeating "withdrew / deposited / Destination
 * account is busy. Trying to refund money..." lines FOREVER, and neither
 * thread ever prints "Transfer successful". Kill it with Ctrl-C.
 *
 * Compare: run Main (both transfers A->B) and it completes fine.
 */
public class LivelockDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount accountA = new BankAccount("1", "John Doe", 1000.0);
        BankAccount accountB = new BankAccount("2", "Jane Smith", 2000.0);

        // Crossing directions — this is the symmetric contention that livelocks.
        Thread t1 = new Thread(new Transfer(accountA, accountB, 10.0), "T1 (A->B)");
        Thread t2 = new Thread(new Transfer(accountB, accountA, 20.0), "T2 (B->A)");

        t1.start();
        // Offset the second thread so their 1000ms in-lock windows overlap
        // out of phase — this makes the livelock reproducible rather than lucky.
        Thread.sleep(1000);
        t2.start();

        // Watchdog: if the transfers were going to finish, they'd do so quickly.
        // If we're still running after 15s, it's livelocked.
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(15_000);
                System.out.println();
                System.out.println(">>> 15s elapsed and neither transfer completed. This is a LIVELOCK.");
                System.out.println(">>> Threads are busy (running + sleeping), just never making progress.");
                System.out.println(">>> Press Ctrl-C to stop.");
            } catch (InterruptedException ignored) {
            }
        }, "watchdog");
        watchdog.setDaemon(true);
        watchdog.start();

        t1.join();
        t2.join();

        // Unreachable while livelocked:
        accountA.viewBalance();
        accountB.viewBalance();
    }
}
