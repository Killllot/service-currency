package org.example.currencyrateclient.model;

public enum RateType {
    CBR("cbr"),
    TKF("tkf");

    String serviceName;

    RateType(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
