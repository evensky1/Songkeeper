package com.innowise.integrator;

import com.innowise.integrator.model.SongCredits;
import com.innowise.integrator.model.TokenResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.apache.camel.http.common.HttpMethods;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MainRouter extends RouteBuilder {

    private final EndpointProvider endpointProvider;
    private String authHeader;
    private Object backupBody;

    @Override
    public void configure() {

        onException(HttpOperationFailedException.class)
            .maximumRedeliveries(1)
            .removeHeader(Exchange.HTTP_QUERY)
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
            .process(exchange -> {
                backupBody = exchange.getIn().getBody();

                var requestString = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s",
                    System.getenv("SPOTIFY_CLIENT_ID"),
                    System.getenv("SPOTIFY_CLIENT_SECRET")
                );

                exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
                exchange.getIn().setBody(requestString);
            })
            .to(System.getenv("SPOTIFY_AUTH_URL"))
            .unmarshal().json(JsonLibrary.Jackson, TokenResponse.class)
            .process(exchange -> {

                var tokenResponse = exchange.getIn().getBody(TokenResponse.class);
                log.info("New token was received: {}", tokenResponse);

                authHeader = tokenResponse.tokenType() + " " + tokenResponse.accessToken();
                exchange.getIn().setHeader("Authorization", authHeader);

                exchange.getIn().setBody(backupBody);
            })
            .to(System.getenv("SPOTIFY_SEARCH_URL"))
            .continued(true);

        int enricherServicePort = Integer.parseInt(System.getenv("ENRICHER_SERVICE_PORT"), 10);
        restConfiguration()
            .host("localhost")
            .port(enricherServicePort);

        var fileToEnricherEndpoint =
            endpointProvider.getSqs2Endpoint(System.getenv("FILE_TO_ENRICHER_QUEUE_URL"));

        var fileInfoEndpoint =
            endpointProvider.getHttpEndpoint("/api/v1/file", HttpMethods.POST);

        var songInfoEndpoint =
            endpointProvider.getHttpEndpoint("/api/v1/track", HttpMethods.POST);

        from(fileToEnricherEndpoint)
            .log("FileInfo: ${body}")
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
            .to(fileInfoEndpoint)
            .log("SongCredits: ${body}")
            .unmarshal().json(JsonLibrary.Jackson, SongCredits.class)
            .process(exchange -> {
                var songCredits = exchange.getIn().getBody(SongCredits.class);
                exchange.getIn().setHeader(Exchange.HTTP_QUERY, createSpotifySearchQuery(songCredits));
                exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpMethods.GET);
                exchange.getIn().setHeader("Authorization", authHeader);
                exchange.getIn().setBody(null);
            })
            .to(System.getenv("SPOTIFY_SEARCH_URL"))
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
            .removeHeader(Exchange.HTTP_QUERY)
            .to(songInfoEndpoint);

        int songServicePort = Integer.parseInt(System.getenv("SONG_SERVICE_PORT"), 10);
        restConfiguration().port(songServicePort);

        var enricherToSongEndpoint = endpointProvider.getSqs2Endpoint(System.getenv("ENRICHER_TO_SONG_QUEUE_URL"));

        var saveTrackInfoEndpoint = endpointProvider.getHttpEndpoint("/api/v1/track", HttpMethods.POST);

        from(enricherToSongEndpoint)
            .to(saveTrackInfoEndpoint);

    }

    private String createSpotifySearchQuery(SongCredits songCredits) {

        return "q=" + URLEncoder.encode(String.format("remaster%%20track:%s%%20artist:%s",
            songCredits.track(), songCredits.artist()), StandardCharsets.UTF_8)
            + "&type=track&market=ES&limit=1&offset=0";
    }

}
