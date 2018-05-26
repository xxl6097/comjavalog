package com.java.log;


public class Main {

    public static Thread testThread = null;

    public static void main(String[] args) {
        JavaLog.startLog(8126);
        testThread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {
                        System.out.println("what's up..");
                        Thread.sleep(1111);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        testThread.start();
    }
}
