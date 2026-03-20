package com.erickson.qbe_projection.rest;

import com.erickson.qbe_projection.QbeProjectionApplication;
import com.erickson.qbe_projection.dto.AuthorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = QbeProjectionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorControllerIT {
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void findById_NoBooks() {
        final Long authorId = 1L;
        String url = createURLWithPort("/v1/author/id/" + authorId);
        AuthorResponse authorResponse = restTemplate.getForEntity(url, AuthorResponse.class).getBody();

        assertNotNull(authorResponse);
        assertEquals(authorId, authorResponse.getAuthorId());
        assertEquals("Agatha", authorResponse.getFirstName());
        assertEquals("Christie", authorResponse.getLastName());
        assertEquals("agatha", authorResponse.getUsername());
        assertEquals("agatha@test.net", authorResponse.getEmail());

        assertTrue(authorResponse.getBooks().isEmpty());
    }

    @Test
    void findById_NotFound() {
        String url = createURLWithPort("/v1/author/id/11111");

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Unable to find 11111", response.getBody());
    }
}