/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import com.vkeonline.aws.awsinaction.autoconfigure.AwsClientProperties;
import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.client.config.SdkAdvancedClientOption;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

/**
 * @author Xiaojun.Yang
 */
public class AwsClientBuilderConfigurer {

    private final AwsCredentialsProvider credentialsProvider;
    private final AwsRegionProvider regionProvider;
    private final AwsProperties awsProperties;
    private final ClientOverrideConfiguration clientOverrideConfiguration;

    AwsClientBuilderConfigurer(
        AwsCredentialsProvider credentialsProvider,
        AwsRegionProvider regionProvider,
        AwsProperties awsProperties) {
        this.credentialsProvider = credentialsProvider;
        this.regionProvider = regionProvider;
        this.awsProperties = awsProperties;
        this.clientOverrideConfiguration = ClientOverrideConfiguration.builder()
            .putAdvancedOption(SdkAdvancedClientOption.USER_AGENT_SUFFIX, "hytrust").build();
    }

    public <T extends AwsClientBuilder<?, ?>> T configure(T builder) {
        return configure(builder, null, null);
    }

    public <T extends AwsClientBuilder<?, ?>> T configure(
        T builder,
        @Nullable AwsClientProperties clientProperties,
        @Nullable AwsClientCustomizer<T> customizer) {

        Assert.notNull(builder, "builder is required");
        Assert.notNull(clientProperties, "clientProperties are required");

        builder.credentialsProvider(this.credentialsProvider)
            .region(resolveRegion(clientProperties))
            .overrideConfiguration(this.clientOverrideConfiguration);
        Optional.ofNullable(this.awsProperties.getEndpoint()).ifPresent(builder::endpointOverride);
        Optional.ofNullable(clientProperties)
            .map(AwsClientProperties::getEndpoint)
            .ifPresent(builder::endpointOverride);

        Optional.ofNullable(this.awsProperties.getDefaultsMode()).ifPresent(builder::defaultsMode);
        Optional.ofNullable(this.awsProperties.getFipsEnabled()).ifPresent(builder::fipsEnabled);
        Optional.ofNullable(this.awsProperties.getDualstackEnabled())
            .ifPresent(builder::dualstackEnabled);

        if (customizer != null) {
            AwsClientCustomizer.apply(customizer, builder);
        }
        return builder;
    }

    public Region resolveRegion(@Nullable AwsClientProperties clientProperties) {
        return clientProperties != null && StringUtils.hasLength(clientProperties.getRegion())
            ? Region.of(clientProperties.getRegion())
            : this.regionProvider.getRegion();
    }
}
