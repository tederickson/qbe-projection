package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.BookResponse;
import com.erickson.qbe_projection.dto.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

            if (book.getBookId() == 4) {
                assertEquals("Sense and Sensibility", book.getTitle());
                assertEquals("3 sisters and their widowed mother", book.getContent());
                assertEquals(LocalDate.parse("1811-01-01"), book.getPublishedOn());
            }
            else if (book.getBookId() == 5) {
                assertEquals("Pride and Prejudice", book.getTitle());
                assertEquals("Elizabeth Bennet navigates society, family, and marriage", book.getContent());
                assertEquals(LocalDate.parse("1813-01-01"), book.getPublishedOn());
            }
            else {
                fail(book.toString());
            }
        }
        assertEquals(2, authorResponse.getBooks().size());
    }

    @Test
    void findById() {
        final Long authorId = 2L;
        AuthorResponse authorResponse = authorService.findById(authorId);

        assertEquals(authorId, authorResponse.getAuthorId());
        assertEquals("Alexandre", authorResponse.getFirstName());
        assertEquals("Dumas", authorResponse.getLastName());
        assertEquals("alexandre", authorResponse.getUsername());
        assertEquals("alex@test.net", authorResponse.getEmail());

        for (BookResponse book : authorResponse.getBooks()) {
            if (book.getBookId() == 1) {
                assertEquals("The Three Musketeers", book.getTitle());
                assertEquals("4 guys save the Queen", book.getContent());
                assertEquals(LocalDate.parse("1844-01-25"), book.getPublishedOn());
                assertTrue(book.getComments().isEmpty());
            }
            else if (book.getBookId() == 2) {
                assertEquals("The Count of Monte Cristo", book.getTitle());
                assertEquals("The victim of betrayal by friends", book.getContent());
                assertEquals(LocalDate.parse("1844-08-05"), book.getPublishedOn());
                assertTrue(book.getComments().isEmpty());
            }
            else if (book.getBookId() == 3) {
                assertEquals("The Man in the Iron Mask", book.getTitle());
                assertEquals("The Musketeers replace the cruel King with his twin", book.getContent());
                assertEquals(LocalDate.parse("1847-12-05"), book.getPublishedOn());

                for (CommentResponse comment : book.getComments()) {
                    System.out.println("comment = " + comment);
                    switch (comment.getCommentId().intValue()) {
                        case 1 -> {
                            assertEquals("george", comment.getName());
                            assertEquals("Such a twist!", comment.getContent());
                            assertEquals(LocalDateTime.parse("1944-11-25T00:00"), comment.getPublishedOn());
                            assertNull(comment.getUpdatedOn());
                        }
                        case 2 -> {
                            assertEquals("carl", comment.getName());
                            assertEquals("The victim of betrayal by his own brother", comment.getContent());
                            assertEquals(LocalDateTime.parse("1962-08-05T00:00"), comment.getPublishedOn());
                            assertNotNull(comment.getUpdatedOn());
                        }
                        case 3 -> {
                            assertEquals("roberto", comment.getName());
                            assertEquals("The Musketeers ride again", comment.getContent());
                            assertEquals(LocalDateTime.parse("2022-12-05T00:00"), comment.getPublishedOn());
                            assertNotNull(comment.getUpdatedOn());
                        }

                        default -> fail(comment.toString());
                    }
                }
                assertEquals(3, book.getComments().size());
            }
            else {
                fail(book.toString());
            }
        }
        assertEquals(3, authorResponse.getBooks().size());
    }
}