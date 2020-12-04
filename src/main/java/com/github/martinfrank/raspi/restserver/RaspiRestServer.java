package com.github.martinfrank.raspi.restserver;

import com.github.martinfrank.raspi.restserver.health.SimpleHealthCheck;
import com.github.martinfrank.raspi.restserver.model.Devices;
import com.github.martinfrank.raspi.restserver.resource.DeviceControlResource;
import com.github.martinfrank.raspi.restserver.resource.DeviceConfigurationResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    public void run(RaspiRestServerConfiguration configuration, Environment environment) {

        final ControllerManager controllerManager = new ControllerManager(configuration);
        environment.lifecycle().manage(controllerManager);

        Devices model = new Devices(configuration, controllerManager.getController());

        final DeviceControlResource deviceControlResource = new DeviceControlResource(model);
        environment.jersey().register(deviceControlResource);

        final DeviceConfigurationResource configurationResource = new DeviceConfigurationResource(configuration);
        environment.jersey().register(configurationResource);

        final SimpleHealthCheck healthCheck =
                new SimpleHealthCheck("test");
        environment.healthChecks().register("template", healthCheck);


    }
}
