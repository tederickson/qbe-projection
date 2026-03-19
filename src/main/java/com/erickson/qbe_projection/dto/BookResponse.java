package com.erickson.qbe_projection.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookResponse {
    private Long bookId;

    private String title;
    private String content;
    private LocalDate publishedOn;

    private List<CommentResponse> comments;
}
