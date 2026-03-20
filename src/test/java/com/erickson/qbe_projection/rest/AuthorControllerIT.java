package com.erickson.qbe_projection.rest;

import com.erickson.qbe_projection.QbeProjectionApplication;
import com.erickson.qbe_projection.dto.AuthorDTO;
import com.erickson.qbe_projection.dto.AuthorRequest;
import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.AuthorResponses;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    @Test
    void findBy_MissingPagination() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Charles");

        String url = createURLWithPort("/v1/author/");
        AuthorResponses authorResponses =
                restTemplate.postForEntity(url, authorRequest, AuthorResponses.class).getBody();

        assertNotNull(authorResponses);
        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(0, authorResponses.getCurrentPage());
        assertEquals(1, authorResponses.getTotalPages());
        assertEquals(7, authorResponses.getAuthors().size());

        List<String> expectedLastNames = List.of("Stross", "Frazier", "Bowden", "Perrault", "Dickens", "Mann", "Finch");
        for (var author : authorResponses.getAuthors()) {
            assertEquals("Charles", author.getFirstName());
            assertTrue(expectedLastNames.contains(author.getLastName()));
            assertTrue(author.getEmail().contains("@"));
            assertNotNull(author.getAuthorId());
            assertTrue(author.getBooks().isEmpty());
        }
    }

    @Test
    void findBy_UserName() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setUsername("charles");
        authorRequest.setPageNumber(0);
        authorRequest.setPageSize(3);

        String url = createURLWithPort("/v1/author/");
        AuthorResponses authorResponses =
                restTemplate.postForEntity(url, authorRequest, AuthorResponses.class).getBody();

        assertNotNull(authorResponses);

        assertEquals(1, authorResponses.getTotalElements());
        assertEquals(0, authorResponses.getCurrentPage());
        assertEquals(1, authorResponses.getTotalPages());
        assertEquals(1, authorResponses.getAuthors().size());

        AuthorResponse authorResponse = authorResponses.getAuthors().getFirst();
        assertEquals(4L, authorResponse.getAuthorId());
        assertEquals("Charles", authorResponse.getFirstName());
        assertEquals("Dickens", authorResponse.getLastName());
        assertEquals("charles", authorResponse.getUsername());
        assertEquals("charles@test.net", authorResponse.getEmail());
        assertTrue(authorResponse.getBooks().isEmpty());
    }

    @Test
    void findBy_NotFound() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setUsername("dickens");
        authorRequest.setPageNumber(0);
        authorRequest.setPageSize(3);

        String url = createURLWithPort("/v1/author/");
        AuthorResponses authorResponses =
                restTemplate.postForEntity(url, authorRequest, AuthorResponses.class).getBody();

        assertNotNull(authorResponses);

        assertEquals(0, authorResponses.getTotalElements());
        assertEquals(0, authorResponses.getCurrentPage());
        assertEquals(0, authorResponses.getTotalPages());
        assertEquals(0, authorResponses.getAuthors().size());
    }

    @Test
    void findByFirstName_NotFound() {
        String url = createURLWithPort("/v1/author/first_name/suess");
        ResponseEntity<List<AuthorDTO>> responseEntity = restTemplate.exchange(url,
                                                                               HttpMethod.GET,
                                                                               null,
                                                                               new ParameterizedTypeReference<>() {});
        List<AuthorDTO> authorDTOs = responseEntity.getBody();

        assertNotNull(authorDTOs);
        assertTrue(authorDTOs.isEmpty());
    }

    @Test
    void findByFirstName() {
        String url = createURLWithPort("/v1/author/first_name/Agatha");

        ResponseEntity<List<AuthorDTO>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null, // HttpEntity (optional, e.g., for sending headers)
                new ParameterizedTypeReference<>() {}
        );

        List<AuthorDTO> authorDTOs = responseEntity.getBody();

        assertNotNull(authorDTOs);
        assertEquals(1, authorDTOs.size());

        AuthorDTO authorDto = authorDTOs.getFirst();
        assertEquals("Agatha", authorDto.firstName());
        assertEquals("Christie", authorDto.lastName());
        assertEquals("agatha@test.net", authorDto.email());
    }
}