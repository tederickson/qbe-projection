package com.erickson.qbe_projection.rest;

import com.erickson.qbe_projection.dto.BookRequest;
import com.erickson.qbe_projection.dto.BookResponses;
import com.erickson.qbe_projection.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/books")
public class BookController {
    private final BookService bookService;

    @PostMapping("/")
    public BookResponses getBooksContainingTitle(@RequestBody BookRequest bookRequest) {
        return bookService.getBooksContainingTitle(bookRequest);
    }
}
