package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.mapper.AuthorMapper;
import com.erickson.qbe_projection.model.AuthorEntity;
import com.erickson.qbe_projection.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        return authorEntityOptional.map(AuthorMapper::mapAuthorEntityToAuthorResponse).orElse(null);
    }
}
