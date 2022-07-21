/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import static com.vkeonline.aws.awsinaction.autoconfigure.core.AwsProperties.CONFIG_PREFIX;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import software.amazon.awssdk.awscore.defaultsmode.DefaultsMode ;
/**
 * @author Xiaojun.Yang
 */

@ConfigurationProperties(CONFIG_PREFIX)
public class AwsProperties {

    public static final String CONFIG_PREFIX = "spring.cloud.aws";

    @Nullable
    private Boolean dualstackEnabled;

    @Nullable
    private URI endpoint;

    @Nullable
    private Boolean fipsEnabled;

    @Nullable
    private DefaultsMode defaultsMode;

    @Nullable
    public URI getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(@Nullable URI endpoint) {
        this.endpoint = endpoint;
    }

    @Nullable
    public DefaultsMode getDefaultsMode() {
        return defaultsMode;
    }

    public void setDefaultsMode(@Nullable DefaultsMode defaultsMode) {
        this.defaultsMode = defaultsMode;
    }


    @Nullable
    public Boolean getDualstackEnabled() {
        return dualstackEnabled;
    }

    public void setDualstackEnabled(@Nullable Boolean dualstackEnabled) {
        this.dualstackEnabled = dualstackEnabled;
    }

    @Nullable
    public Boolean getFipsEnabled() {
        return fipsEnabled;
    }

    public void setFipsEnabled(@Nullable Boolean fipsEnabled) {
        this.fipsEnabled = fipsEnabled;
    }
}
