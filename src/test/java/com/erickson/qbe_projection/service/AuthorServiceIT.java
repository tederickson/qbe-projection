package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
// @Sql("/data/InitializeTests.sql")
class AuthorServiceIT {

    @Autowired
    private AuthorService authorService;

    @Test
    void findById_notFound() {
        assertNull(authorService.findById(-1001L));
    }

    @Test
    void findById_NoBooks() {
        AuthorResponse authorResponse = authorService.findById(1L);

        assertEquals(1L, authorResponse.getAuthorId());
        assertEquals("Agatha", authorResponse.getFirstName());
        assertEquals("Christie", authorResponse.getLastName());
        assertEquals("agatha", authorResponse.getUsername());
        assertEquals("agatha@test.net", authorResponse.getEmail());

        assertTrue(authorResponse.getBooks().isEmpty());
    }
}