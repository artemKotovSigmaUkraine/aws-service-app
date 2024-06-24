package org.acme.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@ConfigProperties(prefix = "aws")
public class AwsConfiguration {

    @ConfigProperty(name = "region")
    String region;

    @ConfigProperty(name = "accessKey")
    String accessKey;

    @ConfigProperty(name = "secretAccessKey")
    String secretAccessKey;

    public String getRegion() {
        return region;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }
}
