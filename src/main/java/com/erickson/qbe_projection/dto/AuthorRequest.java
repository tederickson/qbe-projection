package com.erickson.qbe_projection.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public final class AuthorRequest extends PageableRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
}
