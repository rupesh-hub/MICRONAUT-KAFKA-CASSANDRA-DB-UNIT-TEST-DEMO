package com.rupesh;

import com.rupesh.mdel.CustomResponse;
import com.rupesh.mdel.UserResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@MicronautTest
class UserResourceTest {

    EmbeddedServer server;
    HttpClient client;
    String host;
    int port;

    @BeforeEach
    void setup() {
        host = server.getHost();
        port = server.getPort();
        server = mock(EmbeddedServer.class);
        client = mock(HttpClient.class);
    }

    @Test
    void testString() {
        String uri = "http://" + host + ":" + port + "/user-api/v1/users";
        HttpRequest<?> request = HttpRequest.GET(UriBuilder.of(uri).build());
        String message = client.toBlocking().retrieve(request, String.class);
        String expected = "Hello World";
        assertEquals(expected, message);
    }

    @Test
    void testFindAllUser() {
        String uri = "http://" + host + ":" + port + "/user-api/v1/users/all";

        HttpRequest<?> request = HttpRequest.GET(UriBuilder.of(uri).build());
        CustomResponse<List<UserResponse>> message = client.toBlocking().retrieve(request, CustomResponse.class);
        int expectedSize = 4;
        assertEquals(expectedSize, message.getData().size());
    }

}
