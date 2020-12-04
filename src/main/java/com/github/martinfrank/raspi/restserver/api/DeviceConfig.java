package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceConfig {

    @JsonProperty
    public String deviceName;

    @JsonProperty
    public String unit;

    @JsonProperty
    public String notes;

    public DeviceConfig() {
        // Jackson deserialization
    }

    public DeviceConfig(String deviceName, String unit, String notes) {
        this.deviceName = deviceName;
        this.unit = unit;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DeviceConfig{" +
                "deviceName='" + deviceName + '\'' +
                ", values='" + unit + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
