package com.erickson.qbe_projection.dto;

import lombok.Data;

@Data
public class PageableResponse {
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
