package com.erickson.qbe_projection.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookRequest extends PageableRequest {
    private String title;
}
