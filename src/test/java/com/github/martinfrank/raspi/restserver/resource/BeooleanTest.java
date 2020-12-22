package com.github.martinfrank.raspi.restserver.resource;

import org.junit.Test;

public class BeooleanTest {

    @Test
    public void doIt(){
        executeBoolOp(false, false);
        executeBoolOp(false, true);
        executeBoolOp(true, false);
        executeBoolOp(true, true);
    }

    private void executeBoolOp(boolean bool, boolean invert) {
        boolean result = bool ^ invert;
        System.out.println(""+bool+" ^ "+invert+" = "+result);
    }
}
