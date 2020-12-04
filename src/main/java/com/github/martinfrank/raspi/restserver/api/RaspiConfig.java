package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

import java.util.Arrays;

public class RaspiConfig {

    @JsonProperty
    private DeviceConfig[] devices;

    public RaspiConfig() {
        // Jackson deserialization
    }

    public RaspiConfig(DeviceConfig[] devices) {
        this.devices = devices;
    }

    @Override
    public String toString() {
        return "RaspiConfig{" +
                "devices=" + Arrays.toString(devices) +
                '}';
    }
}
