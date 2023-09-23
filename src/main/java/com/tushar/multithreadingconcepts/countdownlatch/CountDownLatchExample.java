package com.tushar.multithreadingconcepts.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(3);
        List<Future<String>> futures = new ArrayList<>(3);

        List<String> vendorList = List.of("MakeMyTrip", "EaseMyTrip", "Ixigo");
        futures.add(executorService.submit(new PriceAccumulator(latch, vendorList.get(0), 1000L)));
        futures.add(executorService.submit(new PriceAccumulator(latch, vendorList.get(1), 2000L)));
        futures.add(executorService.submit(new PriceAccumulator(latch, vendorList.get(2), 3000L)));
        latch.await(2200, TimeUnit.MILLISECONDS);

        System.out.println("Price fetched from below vendors. Ready for price-comparison.");
        futures.forEach(stringFuture -> {
            if (stringFuture.isDone()) {
                try {
                    System.out.println(stringFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            } else {
                stringFuture.cancel(true);
                System.out.println("Time up for price accumulation service. Cancelling call.");
            }
        });
    }
}

class PriceAccumulator implements Callable<String> {

    CountDownLatch latch;
    String name;
    Long time;

    public PriceAccumulator(CountDownLatch latch, String name, Long time) {
        this.latch = latch;
        this.name = name;
        this.time = time;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Calling price service of " + name);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String s = "Fetched price from " + name;
        latch.countDown();
        return s;
    }
}
