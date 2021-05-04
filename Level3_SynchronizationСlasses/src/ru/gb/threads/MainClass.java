package ru.gb.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainClass {
    public static int CARS_COUNT = 4;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
        int carsForCyclicBarrier = CARS_COUNT;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(carsForCyclicBarrier+1);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60, cyclicBarrier), new Tunnel(), new Road(40, cyclicBarrier));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), countDownLatch);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        countDownLatch.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        cyclicBarrier.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
