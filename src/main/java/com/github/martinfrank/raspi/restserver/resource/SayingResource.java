package com.github.martinfrank.raspi.restserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.martinfrank.raspi.restserver.api.Saying;
import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/saying")
@Produces(MediaType.APPLICATION_JSON)
public class SayingResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public SayingResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Saying getExample(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }
}
