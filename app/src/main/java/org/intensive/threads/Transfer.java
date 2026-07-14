package org.intensive.threads;

public class Transfer implements Runnable {
    private final BankAccount from;
    private final BankAccount to;
    private final double amount;

    public Transfer(BankAccount from, BankAccount to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println("Begin " + Thread.currentThread().getName());
        try {
            from.transfer(to, amount);

            to.viewBalance();
            from.viewBalance();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Transfer " + Thread.currentThread().getName() + " completed");
    }
}
