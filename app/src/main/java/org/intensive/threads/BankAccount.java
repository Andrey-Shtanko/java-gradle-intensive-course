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

    private boolean deposit(double amount) throws InterruptedException {
        if (lock.tryLock()) {
            try {
                balance += amount;
                return true;
            } finally {
                System.out.println(Thread.currentThread().getName() + ": deposited " + amount);
                lock.unlock();
            }
        }else {
            return false;
        }
    }

    private boolean withdraw(double amount) throws InterruptedException {
        if (lock.tryLock()) {
            try {
                balance -= amount;
                return true;
            } finally {
                System.out.println(Thread.currentThread().getName() + ": withdrew " + amount);
                lock.unlock();
            }
        }else {
            return false;
        }
    }

    public void transfer (BankAccount to, double amount) throws InterruptedException {
        boolean transferSuccess = false;

        while (!transferSuccess) {
            if (withdraw(amount)) {
                if (to.deposit(amount)) {
                    System.out.println(Thread.currentThread().getName() + ": transfer " + amount);
                    transferSuccess = true;
                } else {
                    System.out.println(Thread.currentThread().getName() + " Destination account is busy. Trying to refund money...");
                     boolean refundSuccess = false;
                    while (!refundSuccess) {
                        if (deposit(amount)) {
                            refundSuccess = true;
                        } else {
                            Thread.sleep(Math.toIntExact(Math.round(Math.random() * 10)));
                        }
                    }

                }
            }else {
                System.out.println(Thread.currentThread().getName() + " Destination account is busy.");
                Thread.sleep(Math.toIntExact(Math.round(Math.random() * 10)));
            }
        }
        System.out.println(Thread.currentThread().getName() + ": Transfer successful");
    }
}

