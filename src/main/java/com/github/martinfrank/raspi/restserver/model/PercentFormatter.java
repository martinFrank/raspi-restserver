package com.github.martinfrank.raspi.restserver.model;

import java.text.DecimalFormat;

public class PercentFormatter {
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("###.##%");

    public static String format(double d){
        return PERCENT_FORMAT.format(d*100);
    }
}
