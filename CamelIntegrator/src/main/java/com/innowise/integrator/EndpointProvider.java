package com.innowise.integrator;

import lombok.RequiredArgsConstructor;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndpointProvider {

    public String getSqs2Endpoint(String queueUrl) {
        var sqsUrl = "http://" + System.getenv("SQS_DOMAIN") + "/000000000000/";

        var queueName = queueUrl.substring(sqsUrl.length());

        return String.format("aws2-sqs://%s?queueUrl=%s&amazonSqsClient=#%s&deleteAfterRead=true", queueName, queueUrl, "sqsClient");
    }

    public String getHttpEndpoint(String path, HttpMethods httpMethod) {

        return String.format("rest:%s:%s", httpMethod.toString().toLowerCase(), path);
    }
}
