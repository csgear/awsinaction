/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure;

import java.net.URI;
import org.springframework.lang.Nullable;

/**
 * @author Xiaojun.Yang
 */
public abstract class AwsClientProperties {
    @Nullable
    private URI endpoint;

    @Nullable
    private String region;

    @Nullable
    public URI getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    @Nullable
    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
