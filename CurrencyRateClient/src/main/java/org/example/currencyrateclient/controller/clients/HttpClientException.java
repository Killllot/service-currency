package org.example.currencyrateclient.controller.clients;

public class HttpClientException extends RuntimeException {
    public HttpClientException(String msg) {
        super(msg);
    }
}
