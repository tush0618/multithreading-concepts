package com.tushar.multithreadingconcepts.pingpong;

public class PingPongSynchronized {
    public static void main(String[] args) throws InterruptedException {
        PingPongSynchronized pingPongSynchronized = new PingPongSynchronized();
        new Thread(new Table(pingPongSynchronized, "Ping")).start();
        Thread.sleep(10);
        new Thread(new Table(pingPongSynchronized, "Pong")).start();
    }
}

class Table implements Runnable {
    final PingPongSynchronized pingPongSynchronized;
    String message;

    public Table(PingPongSynchronized pp, String s) {
        this.pingPongSynchronized = pp;
        this.message = s;
    }

    @Override
    public void run() {
        synchronized (pingPongSynchronized) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(message);
                    pingPongSynchronized.notifyAll();
                    pingPongSynchronized.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


