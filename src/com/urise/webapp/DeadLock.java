package com.urise.webapp;

import java.util.Random;

public class DeadLock {

    private final static Account accountA = new Account();
    private final static Account accountB = new Account();
    private final static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> thread(accountA, accountB));
        Thread thread2 = new Thread(() -> thread(accountB, accountA));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        printBalance();
    }

    private static void thread(Account account1, Account account2) {
        for (int i = 0; i < 1000; i++) {
            synchronized (account1) {
                synchronized (account2) {
                    Account.transfer(account1, account2, random.nextInt(1000));
                }
            }
        }
    }

    private static void printBalance() {
        System.out.printf("Acc A balance: %.2f\n", accountA.getBalance());
        System.out.printf("Acc B balance: %.2f\n", accountB.getBalance());
        System.out.printf("Total balance: %.2f\n", accountA.getBalance() + accountB.getBalance());
    }
}

class Account {
    private double balance = 10000.00;

    public static void transfer(Account from, Account to, double amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}