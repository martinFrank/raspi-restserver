package com.github.martinfrank.raspi.restserver.resource;

import com.github.martinfrank.raspi.restserver.api.Confirmation;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;

public class ResponseGenerator {

    public Confirmation success(DeviceControlCommand command) {
        return new Confirmation(
                command.deviceName,
                command.value,
                command.unit,
                true,
                "setting " + command.deviceName + " to " + " was successful!");
    }

    public Confirmation errorException(DeviceControlCommand command, Exception e) {
        return new Confirmation(
                command.deviceName,
                command.value,
                command.unit,
                false,
                "error executing command: " + e);
    }
}
