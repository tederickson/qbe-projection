package com.erickson.qbe_projection.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public final class AuthorRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String username;

    private Pageable pageable;
}
