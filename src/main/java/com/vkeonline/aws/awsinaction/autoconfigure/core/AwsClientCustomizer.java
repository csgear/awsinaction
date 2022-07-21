/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import org.springframework.lang.Nullable;
import software.amazon.awssdk.awscore.client.builder.AwsAsyncClientBuilder;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.awscore.client.builder.AwsSyncClientBuilder;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;

/**
 * @author Xiaojun.Yang
 */
public interface AwsClientCustomizer<T> {

    @Nullable
    default ClientOverrideConfiguration overrideConfiguration() {
        return null;
    }

    @Nullable
    default SdkHttpClient httpClient() {
        return null;
    }

    @Nullable
    default SdkHttpClient.Builder<?> httpClientBuilder() {
        return null;
    }

    @Nullable
    default SdkAsyncHttpClient asyncHttpClient() {
        return null;
    }

    @Nullable
    default SdkAsyncHttpClient.Builder<?> asyncHttpClientBuilder() {
        return null;
    }

    static <V extends AwsClientBuilder<?, ?>> void apply(AwsClientCustomizer<V> configurer, V builder) {
        if (configurer.overrideConfiguration() != null) {
            builder.overrideConfiguration(configurer.overrideConfiguration());
        }

        if (builder instanceof AwsSyncClientBuilder) {
            AwsSyncClientBuilder syncClientBuilder = (AwsSyncClientBuilder) builder;
            if (configurer.httpClient() != null) {
                syncClientBuilder.httpClient(configurer.httpClient());
            }
            if (configurer.httpClientBuilder() != null) {
                syncClientBuilder.httpClientBuilder(configurer.httpClientBuilder());
            }
        }
        else if (builder instanceof AwsAsyncClientBuilder) {
            AwsAsyncClientBuilder asyncClientBuilder = (AwsAsyncClientBuilder) builder;
            if (configurer.asyncHttpClient() != null) {
                asyncClientBuilder.httpClient(configurer.asyncHttpClient());
            }
            if (configurer.httpClientBuilder() != null) {
                asyncClientBuilder.httpClientBuilder(configurer.asyncHttpClientBuilder());
            }
        }
    }
}
