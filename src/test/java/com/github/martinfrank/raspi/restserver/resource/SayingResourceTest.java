package com.github.martinfrank.raspi.restserver.resource;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SayingResourceTest {

    private SayingResource sayingResource;

    @Before
    public void init() {
        sayingResource = new SayingResource("testtemplate %s", Optional.of("name").get());
    }

    @Test
    public void getExampleWithEmptyOptionalReturnsSayingContainingTemplateWithDefaultValue() {
        assertEquals("testtemplate name", sayingResource.getExample(Optional.absent()).getContent());
    }

    @Test
    public void getExampleWithNameReturnsResponsSayingTemplateWithGivenName() {
        assertEquals("testtemplate user", sayingResource.getExample(Optional.of("user")).getContent());
    }
}
