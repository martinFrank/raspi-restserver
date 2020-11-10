package com.github.martinfrank.raspi.restserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpioManager implements Managed {

    private final GpioController gpio;
    private static final Logger LOGGER = LoggerFactory.getLogger(GpioManager.class);

    public GpioManager(RaspiRestServerConfiguration configuration) {
        gpio = GpioFactory.getInstance();
        LOGGER.debug("gpio successfully created");
    }

    @Override
    public void start() throws Exception {
        LOGGER.debug("gpio successfully started");
    }

    @Override
    public void stop() throws Exception {
        gpio.shutdown();
        LOGGER.debug("gpio successfully stopped");
    }

    public GpioController getGpio() {
        return gpio;
    }
}
