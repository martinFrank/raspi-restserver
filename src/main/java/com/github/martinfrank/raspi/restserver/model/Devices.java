package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.pi4j.io.gpio.GpioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Devices {

    private static final Logger LOGGER = LoggerFactory.getLogger(Devices.class);

    public List<Device> devices = new ArrayList<>();

    public Devices(RaspiRestServerConfiguration configuration, GpioController controller) {
        LOGGER.debug("-----------"+configuration.devices);
        for (RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration: configuration.devices){
            if (deviceConfiguration.hardwarePwmConfiguration != null){
                devices.add(new HardwarePwmDevice(deviceConfiguration));
            }
            if (deviceConfiguration.softServoConfiguration != null){
                LOGGER.debug("deviceConfiguration.name"+deviceConfiguration.name);
                LOGGER.debug("deviceConfiguration.notes"+deviceConfiguration.notes);
                LOGGER.debug("deviceConfiguration.unit"+deviceConfiguration.unit);
                LOGGER.debug("deviceConfiguration.softServoConfiguration.pin"+deviceConfiguration.softServoConfiguration.pin);
                LOGGER.debug("deviceConfiguration.softServoConfiguration.min"+deviceConfiguration.softServoConfiguration.min);
                LOGGER.debug("deviceConfiguration.softServoConfiguration.max"+deviceConfiguration.softServoConfiguration.max);
                devices.add(new SoftServoDevice(deviceConfiguration));
            }
            if (deviceConfiguration.digitalOutConfiguration != null){
                LOGGER.debug("deviceConfiguration"+deviceConfiguration);
                LOGGER.debug("deviceConfiguration.digitalOutConfiguratio"+deviceConfiguration.digitalOutConfiguration);
                devices.add(new DigitalOutDevice(deviceConfiguration, controller));
            }
        }
        LOGGER.debug("-----------");
    }


    public Device getDevice(String deviceName) {
        return devices.stream().filter(d -> d.getDeviceName().equals(deviceName)).findAny().orElse(null);
    }
}
