package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiController;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.pi4j.io.gpio.GpioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Devices {

    private static final Logger LOGGER = LoggerFactory.getLogger(Devices.class);

    public List<Device> devices = new ArrayList<>();

    public Devices(RaspiRestServerConfiguration configuration, RaspiController controller) {
        for (RaspiRestServerConfiguration.DeviceConfiguration deviceConfiguration: configuration.devices){
            if (deviceConfiguration.hardwarePwmConfiguration != null){
                devices.add(new HardwarePwmDevice(deviceConfiguration));
            }
            if (deviceConfiguration.softServoConfiguration != null){
                devices.add(new SoftServoDevice(deviceConfiguration));
            }
            if (deviceConfiguration.digitalOutConfiguration != null){
                devices.add(new DigitalOutDevice(deviceConfiguration, controller));
            }
            if (deviceConfiguration.softMotorConfiguration != null){
                devices.add(new SoftMotorDevice(deviceConfiguration, controller));
            }
            if (deviceConfiguration.hardwareServoConfiguration != null){
                devices.add(new HardwareServoDevice(deviceConfiguration, controller));
            }
        }
    }


    public Device getDevice(String deviceName) {
        return devices.stream().filter(d -> d.getDeviceName().equals(deviceName)).findAny().orElse(null);
    }
}
