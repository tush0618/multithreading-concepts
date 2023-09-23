package com.tushar.multithreadingconcepts.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProdConsBlockingQueue {

    public static void main(String[] args) {
        BlockingQueue<String> sharedQueue = new ArrayBlockingQueue<>(10);
        Thread t1 = new Prod(sharedQueue);
        Thread t2 = new Cons(sharedQueue);
        t1.start();
        t2.start();
    }
}

class Prod extends Thread {
    final BlockingQueue<String> q;

    public Prod(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 20; i++) {
                Thread.sleep(10);
                String s = "Hello " + i;
                q.put(s);
                System.out.println("Produced: " + s);
            }
            q.put("Exit");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Cons extends Thread {
    final BlockingQueue<String> q;

    public Cons(BlockingQueue<String> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            String msg;
            while (!(msg = q.take()).equals("Exit")) {
                System.out.println("Consumed: " + msg);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
