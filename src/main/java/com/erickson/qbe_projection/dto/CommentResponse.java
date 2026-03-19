package com.erickson.qbe_projection.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class CommentResponse {
    private Long commentId;

    private String name;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
}
