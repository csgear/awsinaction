/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.profiles.ProfileFile;

/**
 * @author Xiaojun.Yang
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ AwsCredentialsProvider.class, ProfileFile.class })
@EnableConfigurationProperties(CredentialsProperties.class)
public class CredentialsProviderAutoConfiguration {
    private final CredentialsProperties properties;

    public CredentialsProviderAutoConfiguration(CredentialsProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AwsCredentialsProvider credentialsProvider() {
        return createCredentialsProvider(this.properties);
    }

    public static AwsCredentialsProvider createCredentialsProvider(CredentialsProperties properties) {
        final List<AwsCredentialsProvider> providers = new ArrayList<>();

        if (StringUtils.hasText(properties.getAccessKey()) && StringUtils.hasText(properties.getSecretKey())) {
            providers.add(createStaticCredentialsProvider(properties));
        }

        if (properties.isInstanceProfile()) {
            providers.add(InstanceProfileCredentialsProvider.create());
        }

        Profile profile = properties.getProfile();
        if (profile != null && profile.getName() != null) {
            providers.add(createProfileCredentialProvider(profile));
        }

        if (providers.isEmpty()) {
            return DefaultCredentialsProvider.create();
        }
        else {
            return AwsCredentialsProviderChain.builder().credentialsProviders(providers).build();
        }
    }

    private static StaticCredentialsProvider createStaticCredentialsProvider(CredentialsProperties properties) {
        return StaticCredentialsProvider
            .create(AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));
    }

    private static ProfileCredentialsProvider createProfileCredentialProvider(Profile profile) {
        ProfileFile profileFile;
        if (profile.getPath() != null) {
            profileFile = ProfileFile.builder().type(ProfileFile.Type.CREDENTIALS).content(Paths.get(profile.getPath()))
                .build();
        }
        else {
            profileFile = ProfileFile.defaultProfileFile();
        }
        return ProfileCredentialsProvider.builder().profileName(profile.getName()).profileFile(profileFile).build();
    }
}
