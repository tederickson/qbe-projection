package com.erickson.qbe_projection.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PageableRequest {
    private int pageNumber;
    private int pageSize;
    private Sort sort;
}
