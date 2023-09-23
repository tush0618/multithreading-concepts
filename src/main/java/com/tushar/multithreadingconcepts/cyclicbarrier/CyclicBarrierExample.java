package com.tushar.multithreadingconcepts.cyclicbarrier;


import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CyclicBarrier barrier = new CyclicBarrier(3);
        System.out.println("Game starting...");
        List<String> playerlist = List.of("John", "Chris", "Sunny");
        executorService.submit(new CounterStrike(barrier, playerlist.get(0), 1000L));
        executorService.submit(new CounterStrike(barrier, playerlist.get(1), 2000L));
        executorService.submit(new CounterStrike(barrier, playerlist.get(2), 3000L));
        System.out.println("Waiting for game environment to be up.");
    }
}

class CounterStrike implements Runnable {
    CyclicBarrier barrier;
    String name;
    Long time;

    public CounterStrike(CyclicBarrier barrier, String name, Long time) {
        this.barrier = barrier;
        this.name = name;
        this.time = time;
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Waiting for " + name + " to join. Will join in " + time + "ms");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        barrier.await();
        System.out.println("Game started for " + name);
    }
}
