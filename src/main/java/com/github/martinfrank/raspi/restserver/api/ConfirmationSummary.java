package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class ConfirmationSummary {

    @JsonProperty
    private Confirmation[] confirmations;

    public ConfirmationSummary() {
        // Jackson deserialization
    }

    public ConfirmationSummary(Confirmation[] confirmations) {
        this.confirmations = confirmations;
    }

    @Override
    public String toString() {
        return "ConfirmationSummary{" +
                "confirmations=" + Arrays.toString(confirmations) +
                '}';
    }
}
