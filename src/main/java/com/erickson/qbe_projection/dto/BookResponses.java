package com.erickson.qbe_projection.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookResponses extends PageableResponse {
    private List<BookResponse> books;
}
