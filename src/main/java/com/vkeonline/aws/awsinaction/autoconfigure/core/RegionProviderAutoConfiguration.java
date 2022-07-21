/*
 * Copyright (c) 2022 HyTrust Inc. All rights reserved.
 */

package com.vkeonline.aws.awsinaction.autoconfigure.core;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.profiles.ProfileFile;
import software.amazon.awssdk.regions.providers.AwsProfileRegionProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.regions.providers.AwsRegionProviderChain;
import software.amazon.awssdk.regions.providers.DefaultAwsRegionProviderChain;
import software.amazon.awssdk.regions.providers.InstanceProfileRegionProvider;

/**
 * @author Xiaojun.Yang
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ StaticRegionProvider.class, AwsRegionProvider.class, ProfileFile.class })
@EnableConfigurationProperties(RegionProperties.class)
public class RegionProviderAutoConfiguration {

    private final RegionProperties properties;

    public RegionProviderAutoConfiguration(RegionProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AwsRegionProvider regionProvider() {
        return createRegionProvider(this.properties);
    }

    public static AwsRegionProvider createRegionProvider(RegionProperties properties) {
        final List<AwsRegionProvider> providers = new ArrayList<>();

        if (properties.getStatic() != null && properties.isStatic()) {
            providers.add(new StaticRegionProvider(properties.getStatic()));
        }

        if (properties.isInstanceProfile()) {
            providers.add(new InstanceProfileRegionProvider());
        }

        Profile profile = properties.getProfile();
        if (profile != null && profile.getName() != null) {
            providers.add(createProfileRegionProvider(profile));
        }

        if (providers.isEmpty()) {
            return DefaultAwsRegionProviderChain.builder().build();
        }
        else {
            return new AwsRegionProviderChain(providers.toArray(new AwsRegionProvider[0]));
        }
    }

    private static AwsProfileRegionProvider createProfileRegionProvider(Profile profile) {
        Supplier<ProfileFile> profileFileFn = () -> {
            if (profile.getPath() != null) {
                return ProfileFile.builder().type(ProfileFile.Type.CONFIGURATION).content(Paths.get(profile.getPath()))
                    .build();
            }
            else {
                return ProfileFile.defaultProfileFile();
            }
        };
        return new AwsProfileRegionProvider(profileFileFn, profile.getName());
    }

}
