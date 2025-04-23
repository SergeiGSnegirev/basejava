package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {

    private static final int THREADS_NUMBER = 10000;
    private static final Object LOCK = new Object();
    private int counter;

    public static void main(String[] args) throws InterruptedException {

        System.out.println(Thread.currentThread().getName());

        Thread threadMain = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        threadMain.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " +
                Thread.currentThread().getState())).start();

        System.out.println(threadMain.getState());

        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.incrementCounter();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(mainConcurrency.counter);
    }

    private void incrementCounter() {
        synchronized (LOCK) {
            counter++;
        }
    }
}
