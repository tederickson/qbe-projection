package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.PageableRequest;
import com.erickson.qbe_projection.dto.PageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtil {
    public static Pageable getPageable(PageableRequest pageableRequest) {
        int pageSize = pageableRequest.getPageSize() == 0 ? 10 : pageableRequest.getPageSize();

        if (pageableRequest.getSort() == null) {
            return PageRequest.of(pageableRequest.getPageNumber(), pageSize);
        }

        return PageRequest.of(pageableRequest.getPageNumber(), pageSize, pageableRequest.getSort());
    }

    public static <T> void buildPageable(PageableResponse pageableResponse, Page<T> results) {
        pageableResponse.setCurrentPage(results.getNumber());
        pageableResponse.setTotalPages(results.getTotalPages());
        pageableResponse.setTotalElements(results.getTotalElements());
    }
}
