package com.erickson.qbe_projection.mapper;

import com.erickson.qbe_projection.dto.CommentResponse;
import com.erickson.qbe_projection.model.CommentEntity;

public class CommentMapper {
    public static CommentResponse mapCommentEntityToCommentResponse(CommentEntity commentEntity) {
        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setCommentId(commentEntity.getCommentId());
        commentResponse.setName(commentEntity.getName());
        commentResponse.setContent(commentEntity.getContent());
        commentResponse.setPublishedOn(commentEntity.getPublishedOn());
        commentResponse.setUpdatedOn(commentEntity.getUpdatedOn());

        return commentResponse;
    }
}
