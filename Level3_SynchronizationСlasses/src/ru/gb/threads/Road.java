package ru.gb.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Road extends Stage {
    private final CyclicBarrier cyclicBarrier;

    public Road(int length, CyclicBarrier cyclicBarrier) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            if (length == 40) cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
