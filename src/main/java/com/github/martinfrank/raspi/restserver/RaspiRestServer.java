package com.github.martinfrank.raspi.restserver;

import com.github.martinfrank.raspi.restserver.health.SimpleHealthCheck;
import com.github.martinfrank.raspi.restserver.resource.SayingResource;
import com.github.martinfrank.raspi.restserver.resource.ToggleResource;
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
        final GpioManager gpioManager = new GpioManager(configuration);
        environment.lifecycle().manage(gpioManager);

        final SayingResource resource = new SayingResource(
                configuration.getSayingTemplate(),
                configuration.getDefaultSayingName()
        );
        environment.jersey().register(resource);

        final ToggleResource toggleResource = new ToggleResource(configuration, gpioManager.getGpio());
        environment.jersey().register(toggleResource);

        final SimpleHealthCheck healthCheck =
                new SimpleHealthCheck(configuration.getSayingTemplate());
        environment.healthChecks().register("template", healthCheck);


    }
}
