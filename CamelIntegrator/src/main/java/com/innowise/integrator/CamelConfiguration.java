package com.innowise.integrator;

import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@RequiredArgsConstructor
public class CamelConfiguration {

    private final RouteBuilder sqsRouter;
    private final SqsClient sqsClient;

    @Bean
    public CamelContext camelContext(ApplicationContext applicationContext) throws Exception {

        var ctx = new SpringCamelContext(applicationContext);
        ctx.addRoutes(sqsRouter);
        ctx.setAllowUseOriginalMessage(true);
        ctx.getRegistry().bind("sqsClient", sqsClient);

        return ctx;
    }

}
