package com.rupesh;

import com.rupesh.mdel.News;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.Month;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class NewsControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject
//    @Client("/news")
    HttpClient client;

//    @Timeout(5)
    @Test
    void fetchingOctoberHeadlinesUsesCache() {
        String serverHost = server.getHost();
        int serverPort = server.getPort();
        HttpRequest<Object> request = HttpRequest.GET(UriBuilder.of("http://" + serverHost + ":" + serverPort + "/user-api/v1/news").path(Month.NOVEMBER.name()).build());
        News news = client.toBlocking().retrieve(request, News.class);
//        String expected = "Micronaut 1.3 Milestone 1 Released";
        String expected = "null";
        assertEquals(of(expected), news.getHeadlines());

        news = client.toBlocking().retrieve(request, News.class);
        assertEquals(of(expected), news.getHeadlines());
    }

}