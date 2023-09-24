package com.tushar.multithreadingconcepts.phaser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class CountDownLatchPhaser {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        Phaser phaser = new Phaser(1);

        List<String> vendorList = List.of("MakeMyTrip", "EaseMyTrip", "Ixigo");
        service.submit(new FlightPriceAccumulator(phaser ,vendorList.get(0)));
        phaser.bulkRegister(2);

        service.submit(new FlightPriceAccumulator(phaser ,vendorList.get(1)));
        service.submit(new FlightPriceAccumulator(phaser ,vendorList.get(2)));
        phaser.awaitAdvance(0);

        System.out.println("Finished accumulation.");
    }
}

class FlightPriceAccumulator implements Runnable {

    Phaser phaser;
    String name;
    public FlightPriceAccumulator(Phaser phaser, String vendor) {
        this.phaser = phaser;
        this.name = vendor;
    }

    @Override
    public void run() {
        System.out.println("Calling price service of " + name);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Fetched price from " + name);
        phaser.arrive();
    }
}
