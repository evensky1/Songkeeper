package com.innowise.aws.sqs;

import java.net.URI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration(proxyBeanMethods = false)
public class SqsConfiguration {

    @Bean
    public SqsAsyncClient sqsAsyncClient() {

        return SqsAsyncClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.US_EAST_1)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(SqsClient.class)
    public SqsClient sqsClient(SdkHttpClient sdkHttpClient) {

        return SqsClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.US_EAST_1)
            .httpClient(sdkHttpClient)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(SdkHttpClient.class)
    public SdkHttpClient sdkHttpClient() {
        return ApacheHttpClient.create();
    }
}
