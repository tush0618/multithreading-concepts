package com.tushar.multithreadingconcepts.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomicInteger {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        Thread t1 = new Thread(new Add(atomicInteger));
        Thread t2 = new Thread(new Add(atomicInteger));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count: " + atomicInteger.get());
    }
}

class Add implements Runnable {

    AtomicInteger atomicInteger;

    public Add(AtomicInteger count) {
        this.atomicInteger = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            atomicInteger.incrementAndGet();
        }
    }
}