/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

/**
 * @author Xiaojun.Yang
 */
@ConfigurationProperties(prefix = CredentialsProperties.PREFIX)
public class CredentialsProperties {
    public static final String PREFIX = "spring.cloud.aws.credentials";

    @Nullable
    private String accessKey;

    @Nullable
    private String secretKey;

    private boolean instanceProfile = false;

    @Nullable
    private Profile profile;

    @Nullable
    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Nullable
    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isInstanceProfile() {
        return this.instanceProfile;
    }

    public void setInstanceProfile(boolean instanceProfile) {
        this.instanceProfile = instanceProfile;
    }

    @Nullable
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

}
