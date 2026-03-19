package com.erickson.qbe_projection.mapper;

import com.erickson.qbe_projection.dto.BookResponse;
import com.erickson.qbe_projection.model.BookEntity;

public class BookMapper {
    public static BookResponse mapBookEntityToBookResponse(BookEntity bookEntity) {
        BookResponse bookResponse = new BookResponse();

        bookResponse.setBookId(bookEntity.getBookId());
        bookResponse.setContent(bookEntity.getContent());
        bookResponse.setTitle(bookEntity.getTitle());
        bookResponse.setPublishedOn(bookEntity.getPublishedOn());

        if (bookEntity.getComments() != null) {
            bookResponse.setComments(bookEntity.getComments().stream()
                                             .map(CommentMapper::mapCommentEntityToCommentResponse)
                                             .toList());
        }
        return bookResponse;
    }
}
