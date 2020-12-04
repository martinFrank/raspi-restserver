package com.github.martinfrank.raspi.restserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.martinfrank.raspi.restserver.api.Confirmation;
import com.github.martinfrank.raspi.restserver.api.ConfirmationSummary;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommands;
import com.github.martinfrank.raspi.restserver.model.Device;
import com.github.martinfrank.raspi.restserver.model.Devices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/devicecontrol")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeviceControlResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceControlResource.class);

    private final Devices devices;
    private final ResponseGenerator responseGenerator;

    public DeviceControlResource(Devices devices) {
        responseGenerator = new ResponseGenerator();
        this.devices = devices;
    }

    @POST
    @Timed
    public ConfirmationSummary acceptControlCommand(DeviceControlCommands commands) {
        LOGGER.info("commands: " + commands);
        List<Confirmation> confirmations = new ArrayList<>();
        for (DeviceControlCommand command : commands.deviceControlCommands) {
            try {
                Device device = devices.getDevice(command.deviceName);
                LOGGER.info("device: " + device);
                device.set(command.value, command.unit);
                confirmations.add(responseGenerator.success(command));
            }catch (Exception e){
                confirmations.add(responseGenerator.errorException(command, e));
            }
        }
        return new ConfirmationSummary(confirmations.toArray(new Confirmation[0]));
    }


}