package com.github.martinfrank.raspi.restserver;

import com.github.martinfrank.raspi.restserver.health.SimpleHealthCheck;
import com.github.martinfrank.raspi.restserver.model.Devices;
import com.github.martinfrank.raspi.restserver.resource.DeviceControlResource;
import com.github.martinfrank.raspi.restserver.resource.DeviceConfigurationResource;
import com.pi4j.io.i2c.I2CFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class RaspiRestServer extends Application<RaspiRestServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new RaspiRestServer().run(args);
    }

    @Override
    public String getName() {
        return "raspi rest server";
    }

    @Override
    public void initialize(Bootstrap<RaspiRestServerConfiguration> bootstrap) {

    }

    @Override
    public void run(RaspiRestServerConfiguration configuration, Environment environment) throws IOException, I2CFactory.UnsupportedBusNumberException {

        final RaspiController raspiController = new RaspiController(configuration);
        environment.lifecycle().manage(raspiController);

        Devices model = new Devices(configuration, raspiController);

        final DeviceControlResource deviceControlResource = new DeviceControlResource(model);
        environment.jersey().register(deviceControlResource);

        final DeviceConfigurationResource configurationResource = new DeviceConfigurationResource(configuration);
        environment.jersey().register(configurationResource);

        final SimpleHealthCheck healthCheck =
                new SimpleHealthCheck("test");
        environment.healthChecks().register("template", healthCheck);


    }
}
