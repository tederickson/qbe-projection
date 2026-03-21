package com.erickson.qbe_projection.mapper;

import com.erickson.qbe_projection.dto.AuthorRequest;
import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.model.AuthorEntity;

public class AuthorMapper {
    public static AuthorResponse mapAuthorEntityToAuthorResponse(AuthorEntity authorEntity) {
        AuthorResponse authorResponse = new AuthorResponse();

        authorResponse.setAuthorId(authorEntity.getAuthorId());
        authorResponse.setFirstName(authorEntity.getFirstName());
        authorResponse.setLastName(authorEntity.getLastName());
        authorResponse.setEmail(authorEntity.getEmail());
        authorResponse.setUserName(authorEntity.getUserName());

        if (authorEntity.getBooks() != null) {
            authorResponse.setBooks(authorEntity.getBooks().stream()
                                            .map(BookMapper::mapBookEntityToBookResponse)
                                            .toList());
        }
        return authorResponse;
    }

    public static AuthorEntity mapAuthorRequestToAuthorEntity(AuthorRequest authorRequest) {
        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setFirstName(authorRequest.getFirstName());
        authorEntity.setLastName(authorRequest.getLastName());
        authorEntity.setEmail(authorRequest.getEmail());
        authorEntity.setUserName(authorRequest.getUserName());

        return authorEntity;
    }
}
