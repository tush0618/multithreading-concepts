package com.tushar.multithreadingconcepts.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ProdConsWaitNotify {

    public static void main(String[] args) {
        Queue<Integer> sharedQueue = new LinkedList<>();
        int maxsize = 10;
        Thread t1 = new Producer(sharedQueue, maxsize);
        Thread t2 = new Consumer(sharedQueue, maxsize);
        t1.start();
        t2.start();
    }
}

class Producer extends Thread {
    final Queue<Integer> q;
    int maxsize;

    public Producer(Queue<Integer> q, int maxsize) {
        this.q = q;
        this.maxsize = maxsize;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            synchronized (q) {
                if (q.size() == maxsize) {
                    try {
                        System.out.println("Queue full. Producer is waiting for Consumer to fetch data from queue.");
                        q.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int a = new Random().nextInt();
                System.out.println("Produced: " + a);
                q.add(a);
                q.notifyAll();
            }
        }

    }
}

class Consumer extends Thread {
    final Queue<Integer> q;
    int maxsize;

    public Consumer(Queue<Integer> q, int maxsize) {
        this.q = q;
        this.maxsize = maxsize;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchronized (q) {
                if (q.isEmpty()) {
                    try {
                        System.out.println("Queue empty. Consumer is waiting for Producer to add data into queue.");
                        q.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("Consumed: " + q.remove());
                q.notifyAll();
            }
        }
    }
}
