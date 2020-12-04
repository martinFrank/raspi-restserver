package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class DeviceControlCommand {

    public static final String UNIT_STOP = "STOP";
    public static final String UNIT_PERCENT = "PERCENT";
    public static final String UNIT_BOOLEAN = "BOOLEAN";
    public static final String UNIT_TOGGLE = "TOGGLE";
    public static final String UNIT_PULSE_MILLIS = "PULSE_MILLIS";

    @JsonProperty
    public String deviceName;
    @JsonProperty
    public String value;
    @JsonProperty
    public String unit;

    public DeviceControlCommand() {
        // Jackson deserialization
    }

    public DeviceControlCommand(String deviceName, String value, String unit) {
        this.deviceName = deviceName;
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "DeviceControlCommand{" +
                "deviceName='" + deviceName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
