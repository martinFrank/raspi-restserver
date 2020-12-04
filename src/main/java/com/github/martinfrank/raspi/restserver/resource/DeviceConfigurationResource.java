package com.github.martinfrank.raspi.restserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration;
import com.github.martinfrank.raspi.restserver.RaspiRestServerConfiguration.DeviceConfiguration;
import com.github.martinfrank.raspi.restserver.api.DeviceConfig;
import com.github.martinfrank.raspi.restserver.api.RaspiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;


@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceConfigurationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceConfigurationResource.class);

    private final RaspiConfig raspiConfig;

    public DeviceConfigurationResource(RaspiRestServerConfiguration configuration) {
        raspiConfig = createConfig(configuration);
    }

    private RaspiConfig createConfig(RaspiRestServerConfiguration configuration) {
        List<DeviceConfig> deviceConfigs = new ArrayList<>();
        for (DeviceConfiguration deviceConfig : configuration.devices) {
            deviceConfigs.add(new DeviceConfig(
                    deviceConfig.name,
                    deviceConfig.unit,
                    deviceConfig.notes));
        }
        return new RaspiConfig(deviceConfigs.toArray(new DeviceConfig[0]));
    }

    @GET
    @Timed
    public RaspiConfig getConfiguration() {
        return raspiConfig;
    }

}