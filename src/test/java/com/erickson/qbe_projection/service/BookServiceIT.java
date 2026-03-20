package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.BookRequest;
import com.erickson.qbe_projection.dto.BookResponse;
import com.erickson.qbe_projection.dto.BookResponses;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class BookServiceIT {
    @Autowired
    private BookService bookService;

    @Test
    void getBooksContainingTitle() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("the");

        bookRequest.setPageSize(3);
        BookResponses bookResponses = bookService.getBooksContainingTitle(bookRequest);

        assertEquals(3, bookResponses.getTotalElements());
        assertEquals(0, bookResponses.getCurrentPage());
        assertEquals(1, bookResponses.getTotalPages());
        assertEquals(3, bookResponses.getBooks().size());

        for (BookResponse bookResponse : bookResponses.getBooks()) {
            switch (bookResponse.getTitle()) {
                case "The Three Musketeers", "The Count of Monte Cristo", "The Man in the Iron Mask" -> {
                }
                default -> fail("[" + bookResponse.getTitle() + "] is not a valid title");
            }
        }
    }
}