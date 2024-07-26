package com.josemarcellio.stresser;

public class MonitorThread extends Thread {
    private final long duration; // Durasi yang akan dijalankan dalam milidetik

    public MonitorThread(long duration) {
        this.duration = duration;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int previous = HTTPClient.getRequestCount();
        while (!HTTPClient.isFlag() && (System.currentTimeMillis() - startTime) < duration) {
            if (previous + 100 < HTTPClient.getRequestCount() && previous != HTTPClient.getRequestCount()) {
                System.out.printf("Total Request Terkirim: %d Request%n", HTTPClient.getRequestCount());
                previous = HTTPClient.getRequestCount();
            }
        }
        if (HTTPClient.isFlag() || (System.currentTimeMillis() - startTime) >= duration) {
            System.out.println("-- Stress Test Selesai --");
        }
    }
}
