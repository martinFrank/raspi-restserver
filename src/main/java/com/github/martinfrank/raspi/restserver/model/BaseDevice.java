package com.github.martinfrank.raspi.restserver.model;


public abstract class BaseDevice implements Device{

    public final String name;
    public final String unit;
    public final String notes;

    public BaseDevice(String name, String unit, String notes){
        this.name = name;
        this. unit = unit;
        this.notes = notes;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public String toString() {
        return "BaseDevice{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
