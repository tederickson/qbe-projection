package com.erickson.qbe_projection.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorResponses extends PageableResponse {
    private List<AuthorResponse> authors;
}
