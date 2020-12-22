package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiController;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import java.util.concurrent.TimeUnit;

public class DigitalOutDevice extends BaseDevice{

    private final GpioPinDigitalOutput outputPin;
    private boolean isInverted;

    public DigitalOutDevice(String name, String unit, String notes, GpioPinDigitalOutput outputPin, boolean isInverted) {
        super(name, unit, notes);
        this.outputPin = outputPin;
        this.isInverted = isInverted;
    }

    public DigitalOutDevice(RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration, RaspiController controller) {
        this(deviceConfiguration.name,
                deviceConfiguration.unit,
                deviceConfiguration.notes,
                controller.getGpioController().provisionDigitalOutputPin(
                        RaspiPin.getPinByName(deviceConfiguration.digitalOutConfiguration.pin)),
                deviceConfiguration.digitalOutConfiguration.invert);
    }

    @Override
    public void set(String value, String unit) throws NumberFormatException {
        if (DeviceControlCommand.UNIT_BOOLEAN.equalsIgnoreCase(unit)){
            setOutput(value);
        }
        if (DeviceControlCommand.UNIT_PULSE_MILLIS.equalsIgnoreCase(unit)){
            pulseMicros(value);
        }
    }

    private void pulseMicros(String value) {
        int millis = Integer.parseInt(value);
        outputPin.pulse(millis, TimeUnit.MILLISECONDS);
    }

    private void setOutput(String value) {
        boolean state = isInverted ^ Boolean.parseBoolean(value);
        outputPin.setState(state);
    }

    @Override
    public String getValue() {
        return "state:"+outputPin.isHigh();
    }
}
