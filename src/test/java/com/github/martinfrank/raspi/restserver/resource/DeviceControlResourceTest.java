package com.github.martinfrank.raspi.restserver.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommand;
import com.github.martinfrank.raspi.restserver.api.DeviceControlCommands;
import org.junit.Test;

public class DeviceControlResourceTest {

    @Test
    public void doIt() throws JsonProcessingException {
        DeviceControlCommand command1 = new DeviceControlCommand("steering", "-0.4", "PERCENT");
        DeviceControlCommand command2 = new DeviceControlCommand("drive", "0.3", "PERCENT");
        DeviceControlCommands commands = new DeviceControlCommands(new DeviceControlCommand[]{command1, command2});

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(commands);
        System.out.println(json);

        DeviceControlCommands reBuild = mapper.readValue(json, DeviceControlCommands.class);
        System.out.println("rebuild: "+reBuild);
    }
}
