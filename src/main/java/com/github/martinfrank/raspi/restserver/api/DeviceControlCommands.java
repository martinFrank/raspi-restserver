package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class DeviceControlCommands {

    @JsonProperty
    public DeviceControlCommand[] deviceControlCommands;

    public DeviceControlCommands() {
        // Jackson deserialization
    }

    public DeviceControlCommands(DeviceControlCommand[] deviceControlCommands) {
        this.deviceControlCommands = deviceControlCommands;
    }

    @Override
    public String toString() {
        return "DeviceControlCommands{" +
                "deviceControlCommands=" + Arrays.toString(deviceControlCommands) +
                '}';
    }
}
