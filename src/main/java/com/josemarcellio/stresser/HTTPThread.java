package com.josemarcellio.stresser;

public class HTTPThread extends Thread {
    @Override
    public void run() {
        while (!HTTPClient.isFlag()) {
            int code = HTTPClient.httpCall(HTTPClient.getUrl());
            if (code == 500) {
                HTTPClient.setFlag(true);
            }
        }
    }
}
