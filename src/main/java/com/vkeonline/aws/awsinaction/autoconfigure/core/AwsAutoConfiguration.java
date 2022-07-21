/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;

/**
 * @author Xiaojun.Yang
 */

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsProperties.class)
public class AwsAutoConfiguration {
    private final AwsProperties awsProperties;

    public AwsAutoConfiguration(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public AwsClientBuilderConfigurer awsClientBuilderConfigurer(
        AwsCredentialsProvider credentialsProvider,
        AwsRegionProvider awsRegionProvider) {
        return new AwsClientBuilderConfigurer(credentialsProvider, awsRegionProvider, awsProperties);
    }
}
