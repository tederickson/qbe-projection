package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorDTOs;
import com.erickson.qbe_projection.dto.AuthorRequest;
import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.AuthorResponses;
import com.erickson.qbe_projection.exception.ResourceNotFoundException;
import com.erickson.qbe_projection.mapper.AuthorMapper;
import com.erickson.qbe_projection.model.AuthorEntity;
import com.erickson.qbe_projection.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorResponse findById(Long id) {
        Optional<AuthorEntity> authorEntityOptional = authorRepository.findById(id);

        return authorEntityOptional.map(AuthorMapper::mapAuthorEntityToAuthorResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find " + id));
    }

    @Transactional
    public AuthorResponses findBy(AuthorRequest authorRequest) {
        AuthorEntity authorEntity = AuthorMapper.mapAuthorRequestToAuthorEntity(authorRequest);
        Example<AuthorEntity> example = Example.of(authorEntity);

        final Pageable pageable = PageableUtil.getPageable(authorRequest);
        Page<AuthorEntity> results = authorRepository.findAll(example, pageable);

        AuthorResponses authorResponses = new AuthorResponses();

        PageableUtil.buildPageable(authorResponses, results);
        authorResponses.setAuthors(results.stream().map(AuthorMapper::mapAuthorEntityToAuthorResponse).toList());

        return authorResponses;
    }

    public AuthorDTOs findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new ResourceNotFoundException("Unable to find [" + firstName + "]");
        }

        return new AuthorDTOs(authorRepository.findByFirstName(firstName));
    }
}
