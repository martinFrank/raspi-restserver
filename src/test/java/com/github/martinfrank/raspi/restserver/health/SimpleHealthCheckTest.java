package com.github.martinfrank.raspi.restserver.health;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleHealthCheckTest {

    private SimpleHealthCheck simpleHealthCheck;

    @Test
    public void checkReturnsHealthyResultForValidTemplate() throws Exception {
//        simpleHealthCheck = new SimpleHealthCheck("a tempalte %s");
//        assertTrue(simpleHealthCheck.check().isHealthy());
    }

    @Test
    public void checkReturnsUnhealthyResultForInvalidTemplate() throws Exception {
//        simpleHealthCheck = new SimpleHealthCheck("another template");
//        assertFalse(simpleHealthCheck.check().isHealthy());
    }
}
