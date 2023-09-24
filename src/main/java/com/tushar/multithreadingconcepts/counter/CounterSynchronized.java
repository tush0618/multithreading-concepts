package com.tushar.multithreadingconcepts.counter;

public class CounterSynchronized {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(new Incrementer(counter));
        Thread t2 = new Thread(new Incrementer(counter));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count: " + counter.getCount());
    }
}

class Incrementer implements Runnable {

    final Counter count;

    public Incrementer(Counter count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (count) {
                count.incrementCount();
            }
        }
    }
}

class Counter {

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        ++count;
    }
}
