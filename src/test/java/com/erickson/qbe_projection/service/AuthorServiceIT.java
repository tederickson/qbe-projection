package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorDTO;
import com.erickson.qbe_projection.dto.AuthorRequest;
import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.AuthorResponses;
import com.erickson.qbe_projection.dto.BookResponse;
import com.erickson.qbe_projection.dto.CommentResponse;
import com.erickson.qbe_projection.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class AuthorServiceIT {

    @Autowired
    private AuthorService authorService;

    @Test
    void findById_NotFound() {
        var exception = assertThrows(ResourceNotFoundException.class, () -> authorService.findById(-1001L));
        assertEquals("Unable to find -1001", exception.getMessage());
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

    @Test
    void findBy_MissingPagination() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setUsername("alexandre");
        AuthorResponses authorResponses = authorService.findBy(authorRequest);

        assertEquals(1, authorResponses.getTotalElements());
        assertEquals(0, authorResponses.getCurrentPage());
        assertEquals(1, authorResponses.getTotalPages());
        assertEquals(1, authorResponses.getAuthors().size());

        AuthorResponse authorResponse = authorResponses.getAuthors().getFirst();
        assertEquals(2L, authorResponse.getAuthorId());
        assertEquals("Alexandre", authorResponse.getFirstName());
        assertEquals("Dumas", authorResponse.getLastName());
        assertEquals("alexandre", authorResponse.getUsername());
        assertEquals("alex@test.net", authorResponse.getEmail());
        assertEquals(3, authorResponse.getBooks().size());
    }

    @Test
    void findBy_FirstAndLastName() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Charles");
        authorRequest.setLastName("Dickens");
        authorRequest.setPageSize(3);
        AuthorResponses authorResponses = authorService.findBy(authorRequest);

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
    void findBy_PageNotFound() {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Charles");
        authorRequest.setPageNumber(10);
        authorRequest.setPageSize(25);

        AuthorResponses authorResponses = authorService.findBy(authorRequest);

        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(10, authorResponses.getCurrentPage());
        assertEquals(1, authorResponses.getTotalPages());
        assertTrue(authorResponses.getAuthors().isEmpty());
    }

    @Test
    void findBy_FirstName() {
        final AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Charles");

        final int pageSize = 3;
        final Sort sort = Sort.by(Direction.DESC, "lastName");
        int pageNumber = 0;

        authorRequest.setPageNumber(pageNumber);
        authorRequest.setPageSize(pageSize);
        authorRequest.setSort(sort);

        AuthorResponses authorResponses = authorService.findBy(authorRequest);

        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(pageNumber, authorResponses.getCurrentPage());
        assertEquals(3, authorResponses.getTotalPages());
        assertEquals(pageSize, authorResponses.getAuthors().size());

        for (var authorResponse : authorResponses.getAuthors()) {
            assertEquals("Charles", authorResponse.getFirstName());
        }
        assertEquals("Stross", authorResponses.getAuthors().get(0).getLastName());
        assertEquals("Perrault", authorResponses.getAuthors().get(1).getLastName());
        assertEquals("Mann", authorResponses.getAuthors().get(2).getLastName());

        pageNumber++;
        authorRequest.setPageNumber(pageNumber);

        authorResponses = authorService.findBy(authorRequest);

        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(pageNumber, authorResponses.getCurrentPage());
        assertEquals(3, authorResponses.getTotalPages());
        assertEquals(pageSize, authorResponses.getAuthors().size());

        for (var authorResponse : authorResponses.getAuthors()) {
            assertEquals("Charles", authorResponse.getFirstName());
        }
        assertEquals("Frazier", authorResponses.getAuthors().get(0).getLastName());
        assertEquals("Finch", authorResponses.getAuthors().get(1).getLastName());
        assertEquals("Dickens", authorResponses.getAuthors().get(2).getLastName());

        pageNumber++;
        authorRequest.setPageNumber(pageNumber);
        authorResponses = authorService.findBy(authorRequest);

        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(pageNumber, authorResponses.getCurrentPage());
        assertEquals(3, authorResponses.getTotalPages());
        assertEquals(1, authorResponses.getAuthors().size());

        for (var authorResponse : authorResponses.getAuthors()) {
            assertEquals("Charles", authorResponse.getFirstName());
        }
        assertEquals("Bowden", authorResponses.getAuthors().getFirst().getLastName());

        pageNumber++;
        authorRequest.setPageNumber(pageNumber);
        authorResponses = authorService.findBy(authorRequest);

        assertEquals(7, authorResponses.getTotalElements());
        assertEquals(pageNumber, authorResponses.getCurrentPage());
        assertEquals(3, authorResponses.getTotalPages());
        assertEquals(0, authorResponses.getAuthors().size());
    }

    @Test
    void findByFirstName() {
        final List<AuthorDTO> authorResponses = authorService.findByFirstName("Charles");
        assertEquals(7, authorResponses.size());

        List<String> expectedLastNames = List.of("Stross", "Frazier", "Bowden", "Perrault", "Dickens", "Mann", "Finch");
        for (var authorDto : authorResponses) {
            assertEquals("Charles", authorDto.firstName());
            assertTrue(expectedLastNames.contains(authorDto.lastName()));
            assertTrue(authorDto.email().contains("@"));
        }
        // Hibernate does not attempt to access the books tied to the author
        //        Hibernate:
        //        select
        //              ae1_0.first_name,
        //              ae1_0.last_name,
        //              ae1_0.email
        //        from
        //        author ae1_0
        //        where
        //        ae1_0.first_name=?
    }

    @ParameterizedTest
    @NullAndEmptySource
    void findByFirstName(String firstName) {
        final List<AuthorDTO> authorResponses = authorService.findByFirstName(firstName);
        assertTrue(authorResponses.isEmpty());
    }

    @Test
    void findByFirstName_NotFound() {
        final List<AuthorDTO> authorResponses = authorService.findByFirstName("firstName");
        assertTrue(authorResponses.isEmpty());
    }
}