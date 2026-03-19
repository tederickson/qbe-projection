package com.erickson.qbe_projection.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorResponses {
    private List<AuthorResponse> authors;

    private int currentPage;
    private int totalPages;
    private long totalElements;
}
