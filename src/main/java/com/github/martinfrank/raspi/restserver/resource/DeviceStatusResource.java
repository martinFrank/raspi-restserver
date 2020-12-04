package com.github.martinfrank.raspi.restserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration.DeviceConfiguration;
import com.github.martinfrank.raspi.restserver.api.Confirmation;
import com.github.martinfrank.raspi.restserver.api.ConfirmationSummary;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommands;
import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/devicestatus")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceStatusResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceStatusResource.class);

    public DeviceStatusResource(RaspiRestServerConfiguration configuration, GpioController controller){
    }

    @GET
    @Timed
    public ConfirmationSummary acceptControlCommand() {
        LOGGER.info("-------------------------------------------");
        LOGGER.info("requesting devices status: ");
        return new ConfirmationSummary(new Confirmation[] {new Confirmation("command.deviceName", "command.value", "command.unit", false, "unknown device")});

    }

}