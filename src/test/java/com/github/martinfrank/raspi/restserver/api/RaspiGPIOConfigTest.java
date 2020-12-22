package com.github.martinfrank.raspi.restserver.api;

import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.RaspiPin;
import org.junit.Test;

import java.util.Arrays;

public class RaspiGPIOConfigTest {

    @Test
    public void doIt(){
        Arrays.stream(RaspiPin.allPins()).forEach(System.out::println);
        PinMode.all().forEach(System.out::println);

        Arrays.stream(PCA9685Pin.ALL).forEach(System.out::println);
    }
}
