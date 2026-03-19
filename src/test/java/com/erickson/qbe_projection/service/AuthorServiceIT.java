package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        final Long authorId = 1L;
        AuthorResponse authorResponse = authorService.findById(authorId);

        assertEquals(authorId, authorResponse.getAuthorId());
        assertEquals("Agatha", authorResponse.getFirstName());
        assertEquals("Christie", authorResponse.getLastName());
        assertEquals("agatha", authorResponse.getUsername());
        assertEquals("agatha@test.net", authorResponse.getEmail());

        assertTrue(authorResponse.getBooks().isEmpty());
    }

    @Test
    void findById_NoComments() {
        final Long authorId = 3L;
        AuthorResponse authorResponse = authorService.findById(authorId);

        assertEquals(authorId, authorResponse.getAuthorId());
        assertEquals("Jane", authorResponse.getFirstName());
        assertEquals("Austen", authorResponse.getLastName());
        assertEquals("jane", authorResponse.getUsername());
        assertEquals("jane@test.net", authorResponse.getEmail());

        for (BookResponse book : authorResponse.getBooks()) {
            assertTrue(book.getComments().isEmpty());
            assertNull(book.getPublishedOn());

            if (book.getBookId() == 4) {
                assertEquals("Sense and Sensibility", book.getTitle());
                assertEquals("3 sisters and their widowed mother", book.getContent());
            }
            else if (book.getBookId() == 5) {
                assertEquals("Pride and Prejudice", book.getTitle());
                assertEquals("Elizabeth Bennet navigates society, family, and marriage", book.getContent());
            }
            else {
                fail(book.toString());
            }
        }
        assertEquals(2, authorResponse.getBooks().size());
    }
}