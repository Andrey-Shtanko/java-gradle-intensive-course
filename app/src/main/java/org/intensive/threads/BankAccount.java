package org.intensive.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount  {
    final String accountNumber;
    final String accountHolder;
    private double balance;

    private final Lock lock = new ReentrantLock();

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void viewBalance() {
        System.out.println("Account: " + accountNumber + ", Holder: " + accountHolder + ", Balance: " + balance);
    }

    private void deposit(double amount) throws InterruptedException {
        Thread.sleep(1000);
        balance += amount;
        System.out.println(Thread.currentThread().getName() + ": deposited " + amount);
    }

    private void withdraw(double amount) throws InterruptedException {
        Thread.sleep(1000);
        balance -= amount;
        System.out.println(Thread.currentThread().getName() + ": withdrew " + amount);
    }

    public void transfer (BankAccount to, double amount) throws InterruptedException {
        BankAccount firstLock;
        BankAccount secondLock;
        boolean transferSuccess = false;

        if (this.accountNumber.compareTo(to.accountNumber) < 0) {
            firstLock = this;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = this;
        }

        while (!transferSuccess) {
            if (firstLock.lock.tryLock()) {
                try {
                    if (secondLock.lock.tryLock()) {
                        try {
                            withdraw(amount);
                            to.deposit(amount);
                            System.out.println(Thread.currentThread().getName() + ": transferred " + amount);
                            transferSuccess = true;
                        } finally {
                            secondLock.lock.unlock();
                        }
                    }
                } finally {
                    firstLock.lock.unlock();
                }
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Transfer successful");
    }
}
