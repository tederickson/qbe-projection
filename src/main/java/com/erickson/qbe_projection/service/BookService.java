package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.BookRequest;
import com.erickson.qbe_projection.dto.BookResponses;
import com.erickson.qbe_projection.mapper.BookMapper;
import com.erickson.qbe_projection.model.BookEntity;
import com.erickson.qbe_projection.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public BookResponses getBooksContainingTitle(BookRequest bookRequest) {
        final Pageable pageable = PageableUtil.getPageable(bookRequest);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookRequest.getTitle());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);
        Example<BookEntity> example = Example.of(bookEntity, matcher);

        Page<BookEntity> results = bookRepository.findAll(example, pageable);
        BookResponses bookResponses = new BookResponses();

        PageableUtil.buildPageable(bookResponses, results);
        bookResponses.setBooks(results.stream().map(BookMapper::mapBookEntityToBookResponse).toList());

        return bookResponses;
    }
}
