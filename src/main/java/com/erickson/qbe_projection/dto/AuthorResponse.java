package com.erickson.qbe_projection.dto;

import lombok.Data;

import java.util.List;

@Data
public final class AuthorResponse {
    private Long authorId;

    private String firstName;
    private String lastName;
    private String email;
    private String username;

    private List<BookResponse> books;
}
