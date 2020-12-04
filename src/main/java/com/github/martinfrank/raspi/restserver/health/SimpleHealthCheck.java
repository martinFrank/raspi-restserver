package com.github.martinfrank.raspi.restserver.health;

import com.codahale.metrics.health.HealthCheck;

public class SimpleHealthCheck extends HealthCheck {

    private final String template;

    public SimpleHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String message = String.format(template, "TEST");
        //FIXME real check
//        if (!message.contains("TEST")) {
//            return Result.unhealthy("template doesn't include a name");
//        }
        return Result.healthy();
    }
}
