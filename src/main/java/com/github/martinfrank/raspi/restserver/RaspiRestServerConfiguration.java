package com.github.martinfrank.raspi.restserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class RaspiRestServerConfiguration extends Configuration {

    @NotEmpty
    private String sayingTemplate;

    @NotEmpty
    private String defaultSayingName;

    @JsonProperty
    public String getSayingTemplate() {
        return sayingTemplate;
    }

    @JsonProperty
    public void setSayingTemplate(String sayingTemplate) {
        this.sayingTemplate = sayingTemplate;
    }

    @JsonProperty
    public String getDefaultSayingName() {
        return defaultSayingName;
    }

    @JsonProperty
    public void setDefaultSayingName(String name) {
        this.defaultSayingName = name;
    }
}
