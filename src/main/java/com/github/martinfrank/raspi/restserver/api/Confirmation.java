package com.github.martinfrank.raspi.restserver.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Confirmation {

    @JsonProperty
    private String content;
    @JsonProperty
    private String values;
    @JsonProperty
    public String unit;
    @JsonProperty
    private boolean isSuccessful;
    @JsonProperty
    private String result;

    public Confirmation() {
        // Jackson deserialization
    }

    public Confirmation(String content, String values, String unit, boolean isSuccessful, String result) {
        this.content = content;
        this.values = values;
        this.unit = unit;
        this.isSuccessful = isSuccessful;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "content='" + content + '\'' +
                ", values='" + values + '\'' +
                ", unit='" + unit + '\'' +
                ", isSuccessful=" + isSuccessful +
                ", result='" + result + '\'' +
                '}';
    }
}
