package com.innowise.aws.s3;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration(proxyBeanMethods = false)
public class S3Configuration {

    @Bean
    @ConditionalOnMissingBean(S3Client.class)
    public S3Client s3Client(SdkHttpClient sdkHttpClient) throws URISyntaxException {

        var uri = new URI("http://s3.localhost.localstack.cloud:4566");

        return S3Client.builder()
            .endpointOverride(uri)
            .httpClient(sdkHttpClient)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(SdkHttpClient.class)
    public SdkHttpClient sdkHttpClient() {
        return ApacheHttpClient.create();
    }
}
