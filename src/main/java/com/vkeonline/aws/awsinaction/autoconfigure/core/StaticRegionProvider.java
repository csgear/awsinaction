/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import org.springframework.util.Assert;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

/**
 * @author Xiaojun.Yang
 */
public class StaticRegionProvider implements AwsRegionProvider {
    private final Region configuredRegion;

    public StaticRegionProvider(String configuredRegion) {
        Assert.notNull(configuredRegion, "region is required");

        try {
            this.configuredRegion = Region.of(configuredRegion);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The region '" + configuredRegion + "' is not a valid region!", e);
        }
    }

    @Override
    public Region getRegion() {
        return null;
    }
}
