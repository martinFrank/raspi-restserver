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

    }

    public static class PinConfiguration {
        @JsonProperty
        public String pin;
    }

    public static class SoftServoConfiguration extends PinConfiguration{
        @JsonProperty
        public int min;
        @JsonProperty
        public int max;
    }

    public static class SoftMotorConfiguration extends PinConfiguration{
        @JsonProperty
        public String directionalPin;
    }

    public static class HardwarePwmConfiguration extends PinConfiguration{
        @JsonProperty
        public int min;
        @JsonProperty
        public int max;
    }

    public static class DigitalOutConfiguration extends PinConfiguration{
        @JsonProperty
        public String mode;
    }

}
