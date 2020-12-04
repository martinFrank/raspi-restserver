package com.github.martinfrank.raspi.restserver.model;

public interface Device {

    void set(String value, String unit);

    String getValue();

    String getDeviceName();
}
