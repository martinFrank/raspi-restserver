package com.github.martinfrank.raspi.restserver.model;

import com.github.martinfrank.raspi.restserver.RaspiController;
import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

import java.util.Arrays;
import java.util.Locale;

public class PwmDriver {

    private final PinWrapper pinWrapper;
    private final RaspiController raspiController;

    public PwmDriver(RaspiController raspiController, String pinName) {
        this.raspiController = raspiController;
        pinWrapper = new PinWrapper(pinName, raspiController);
    }

    public void setPwm(double percent, int min, int max){
        int pwmValue = normalizePwmValue(percent, min, max);

        if(pinWrapper.isGpioPin){
            Gpio.pwmWrite(pinWrapper.pin.getPin().getAddress(), pwmValue);
        }else if(pinWrapper.isPca9685Pin){
            raspiController.getPca9685GpioProvider().setPwm(pinWrapper.pin.getPin(), pwmValue);
        }
    }

    public void stopPwm(){
        if(pinWrapper.isGpioPin){
            Gpio.pwmWrite(pinWrapper.pin.getPin().getAddress(), 0);
        }else if(pinWrapper.isPca9685Pin){
            raspiController.getPca9685GpioProvider().setPwm(pinWrapper.pin.getPin(), 0);
        }
    }

    private class PinWrapper{
        private static final String GPIO_PREFIX = "GPIO";
        private static final String PCA9685_PREFIX = "PWM";
        private final boolean isGpioPin;
        private final boolean isPca9685Pin;
        private GpioPinPwmOutput pin;

        public PinWrapper(String pinName, RaspiController raspiController) {
            isGpioPin = pinName.toUpperCase(Locale.ROOT).startsWith(GPIO_PREFIX);
            isPca9685Pin = pinName.toUpperCase(Locale.ROOT).startsWith(PCA9685_PREFIX);

            if(isPca9685Pin){
                PCA9685GpioProvider provider = raspiController.getPca9685GpioProvider();
                Pin candidate = Arrays.stream(PCA9685Pin.ALL).filter(p -> p.getName().equals(pinName)).findAny().orElse(null);
                pin = raspiController.getGpioController().provisionPwmOutputPin(provider, candidate);
            }

            if(isGpioPin){
                pin = raspiController.getGpioController().provisionPwmOutputPin(RaspiPin.getPinByName(pinName));
            }
        }
    }

    private int normalizePwmValue(double pwmPercent, int min, int max) {
        if (pwmPercent > 1) {
            pwmPercent = 1;
        }
        if (pwmPercent < -1) {
            pwmPercent = -1;
        }
        double center = (max + min) / 2d;
        double range = (max - min) / 2d;
        return (int) (pwmPercent * range + center);
    }
}
