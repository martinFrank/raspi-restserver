package com.github.martinfrank.raspi.restserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.api.Saying;
import com.google.common.base.Optional;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/toggle")
@Produces(MediaType.APPLICATION_JSON)
public class ToggleResource {

    private final GpioPinDigitalOutput pin;

    public ToggleResource(RaspiRestServerConfiguration configuration, GpioController controller) {
        pin = controller.provisionDigitalOutputPin(
                RaspiPin.GPIO_04,   // PIN NUMBER
                "My LED",     // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)
    }

    @GET
    @Timed
    public Saying getExample(@QueryParam("name") Optional<String> name) {
        pin.toggle();
        return new Saying(0, name.or("test"));
    }
}