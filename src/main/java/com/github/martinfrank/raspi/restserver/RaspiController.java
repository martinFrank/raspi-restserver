package com.github.martinfrank.raspi.restserver;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;
import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class RaspiController implements Managed {

    private final GpioController gpioController;
    private final PCA9685GpioProvider pca9685GpioProvider;
    private static final Logger LOGGER = LoggerFactory.getLogger(RaspiController.class);

    public RaspiController(RaspiRestServerConfiguration configuration) throws IOException, I2CFactory.UnsupportedBusNumberException {
        Gpio.wiringPiSetup();
        gpioController = GpioFactory.getInstance();
        Gpio.pwmSetRange(configuration.pwmSetting.hardwarePwmRange);
        Gpio.pwmSetClock(configuration.pwmSetting.hardwarePwmClock);
        Gpio.pwmSetMode(getPwmMode(configuration.pwmSetting.hardwarePwmMode));
        LOGGER.debug("gpio successfully created");

        System.out.println("<--Pi4J--> PCA9685 PWM Example ... started.");
        // This would theoretically lead into a resolution of 5 microseconds per step:
        // 4096 Steps (12 Bit)
        // T = 4096 * 0.000005s = 0.02048s
        // f = 1 / T = 48.828125
        BigDecimal frequency = new BigDecimal("48.828");
        // Correction factor: actualFreq / targetFreq
        // e.g. measured actual frequency is: 51.69 Hz
        // Calculate correction factor: 51.65 / 48.828 = 1.0578
        // --> To measure actual frequency set frequency without correction factor(or set to 1)
        BigDecimal frequencyCorrectionFactor = new BigDecimal("1.0578");
        // Create custom PCA9685 GPIO provider
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
        pca9685GpioProvider = new PCA9685GpioProvider(bus, 0x40, frequency, frequencyCorrectionFactor);
        // Define outputs in use for this example
//        GpioPinPwmOutput[] myOutputs = provisionPwmOutputs(pca9685GpioProvider);
    }

    @Override
    public void start()  {
        LOGGER.debug("gpio successfully started");
    }

    @Override
    public void stop()  {
        pca9685GpioProvider.shutdown();
        LOGGER.debug("gpio successfully stopped");
    }

    private int getPwmMode(String pwmMode) {
        if ("PWM_MODE_BAL".equalsIgnoreCase(pwmMode)){
            return Gpio.PWM_MODE_BAL;
        }
        //default/invalid mode:
        return Gpio.PWM_MODE_MS;
    }

    public PCA9685GpioProvider getPca9685GpioProvider() {
        return pca9685GpioProvider;
    }

    public GpioController getGpioController() {
        return gpioController;
    }


    private static GpioPinPwmOutput[] provisionPwmOutputs(final PCA9685GpioProvider gpioProvider) {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinPwmOutput[] myOutputs = {
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_00, "Pulse 00"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_01, "Pulse 01"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_02, "Pulse 02"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_03, "Pulse 03"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_04, "Pulse 04"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_05, "Pulse 05"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_06, "Pulse 06"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_07, "Pulse 07"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_08, "Pulse 08"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_09, "Pulse 09"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_10, "Always ON"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_11, "Always OFF"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_12, "Servo pulse MIN"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_13, "Servo pulse NEUTRAL"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_14, "Servo pulse MAX"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_15, "not used")};
        return myOutputs;
    }
}
