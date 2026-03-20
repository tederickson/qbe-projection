package com.erickson.qbe_projection.rest;

import com.erickson.qbe_projection.QbeProjectionApplication;
import com.erickson.qbe_projection.dto.BookRequest;
import com.erickson.qbe_projection.dto.BookResponse;
import com.erickson.qbe_projection.dto.BookResponses;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = QbeProjectionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIT {
    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void findBy_NotFound() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("abracadabra");

        String url = createURLWithPort("/v1/books/");
        BookResponses bookResponses = restTemplate.postForEntity(url, bookRequest, BookResponses.class).getBody();

        assertNotNull(bookResponses);

        assertEquals(0, bookResponses.getTotalElements());
        assertEquals(0, bookResponses.getCurrentPage());
        assertEquals(0, bookResponses.getTotalPages());
        assertEquals(0, bookResponses.getBooks().size());
    }

    @Test
    void findBy() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("THE");

        String url = createURLWithPort("/v1/books/");
        BookResponses bookResponses = restTemplate.postForEntity(url, bookRequest, BookResponses.class).getBody();

        assertNotNull(bookResponses);

        assertEquals(3, bookResponses.getTotalElements());
        assertEquals(0, bookResponses.getCurrentPage());
        assertEquals(1, bookResponses.getTotalPages());
        assertEquals(3, bookResponses.getBooks().size());

        for (BookResponse bookResponse : bookResponses.getBooks()) {
            assertTrue(bookResponse.getTitle().toLowerCase().contains("the"));
        }
    }

    @Test
    void findBy_WalkedOffEdge() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("THE ");
        bookRequest.setPageNumber(200);

        String url = createURLWithPort("/v1/books/");
        BookResponses bookResponses = restTemplate.postForEntity(url, bookRequest, BookResponses.class).getBody();

        assertNotNull(bookResponses);

        assertEquals(3, bookResponses.getTotalElements());
        assertEquals(200, bookResponses.getCurrentPage());
        assertEquals(1, bookResponses.getTotalPages());
        assertTrue(bookResponses.getBooks().isEmpty());
    }
}