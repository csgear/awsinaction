/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author Xiaojun.Yang
 */
@ConfigurationProperties(prefix = RegionProperties.PREFIX)
public class RegionProperties {

    public static final String PREFIX = "spring.cloud.aws.region";
    @Nullable
    private String staticRegion;

    private boolean instanceProfile = false;

    @Nullable
    private Profile profile;

    @Nullable
    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Nullable
    public String getStatic() {
        return this.staticRegion;
    }

    public boolean isStatic() {
        return StringUtils.hasText(this.staticRegion);
    }

    public void setStatic(String staticRegion) {
        this.staticRegion = staticRegion;
    }

    public boolean isInstanceProfile() {
        return this.instanceProfile;
    }

    public void setInstanceProfile(boolean instanceProfile) {
        this.instanceProfile = instanceProfile;
    }

}
