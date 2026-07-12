package org.intensive.threads;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account1 = new BankAccount("1", "John Doe", 1000.0);
        BankAccount account2 = new BankAccount("2", "Jane Smith", 2000.0);

        Thread transfer1 = new Thread(new Transfer(account1, account2, 10.0), "Transfer 1");
        transfer1.start();
        Thread transfer2 = new Thread(new Transfer(account1, account2, 20.0), "Transfer 2");
        transfer2.start();

        transfer1.join();
        transfer2.join();

        account1.viewBalance();
        account2.viewBalance();
    }
}
