package ru.gb.threads;
/*Создать три потока,
каждый из которых выводит определенную букву (A, B и C) 5 раз
(порядок на выходе – ABСABСABС). */

public class PrintABC {

    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {
        PrintABC waitNotifyApp = new PrintABC();

        new Thread(waitNotifyApp::printA).start();
        new Thread(waitNotifyApp::printB).start();
        new Thread(waitNotifyApp::printC).start();
    }
    private void printA() {
        synchronized (mon) {
            try{
                for (int i = 0; i < 5; i++) {
                    while (currentLetter !='A'){
                        mon.wait();
                    }
                    if(i < 3) System.out.print("A");
                    currentLetter = 'B';
                    mon.notifyAll();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printB() {
        synchronized (mon) {
            try{
                for (int i = 0; i < 5; i++) {
                    while (currentLetter !='B'){
                        mon.wait();
                    }
                    if(i < 3) System.out.print("B");
                    currentLetter = 'C';
                    mon.notifyAll();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printC() {
        synchronized (mon) {
            try{
                for (int i = 0; i < 5; i++) {
                    while (currentLetter !='C'){
                        mon.wait();
                    }
                    if(i < 3) System.out.print("C");
                    currentLetter = 'A';
                    mon.notifyAll();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
