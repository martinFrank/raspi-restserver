package com.github.martinfrank.raspi.restserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RaspiRestServerConfiguration extends Configuration {

    @JsonProperty
    public PwmSetting pwmSetting;

    public static class PwmSetting {

        @JsonProperty
        public int softPwmRange;

        @JsonProperty
        public int hardwarePwmClock;

        @JsonProperty
        public int hardwarePwmRange;

        @JsonProperty
        public String hardwarePwmMode;
    }

    @JsonProperty
    public DeviceConfiguration[] devices;

    public static class DeviceConfiguration {

        @JsonProperty
        public String name;
        @JsonProperty
        public String unit;
        @JsonProperty
        public String notes;

        @JsonProperty
        public SoftServoConfiguration softServoConfiguration;

        @JsonProperty
        public HardwarePwmConfiguration hardwarePwmConfiguration;

        @JsonProperty
        public DigitalOutConfiguration digitalOutConfiguration;

        @JsonProperty
        public SoftMotorConfiguration softMotorConfiguration;

        @JsonProperty
        public SoftServoConfiguration hardwareServoConfiguration;

    }

    public static class SoftServoConfiguration{
        @JsonProperty
        public String pin;
        @JsonProperty
        public int min;
        @JsonProperty
        public int max;
        @JsonProperty
        public boolean invert;
    }

    public static class HardwareServoConfiguration {
        @JsonProperty
        public String pin;
        @JsonProperty
        public int min;
        @JsonProperty
        public int max;
        @JsonProperty
        public boolean invert;
    }

    public static class SoftMotorConfiguration {
        @JsonProperty
        public String drivePin;
        @JsonProperty
        public String directionalPin;
        @JsonProperty
        public boolean invert;
    }

    public static class HardwarePwmConfiguration {
        @JsonProperty
        public String pin;
        @JsonProperty
        public int min;
        @JsonProperty
        public int max;
        @JsonProperty
        public boolean invert;
    }

    public static class DigitalOutConfiguration {
        @JsonProperty
        public String pin;
        @JsonProperty
        public boolean invert;
    }



}
