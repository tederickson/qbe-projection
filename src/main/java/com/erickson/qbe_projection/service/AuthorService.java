package com.erickson.qbe_projection.service;

import com.erickson.qbe_projection.dto.AuthorDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    private static Pageable getPageable(AuthorRequest authorRequest) {
        int pageSize = authorRequest.getPageSize() == 0 ? 10 : authorRequest.getPageSize();

        if (authorRequest.getSort() == null) {
            return PageRequest.of(authorRequest.getPageNumber(), pageSize);
        }

        return PageRequest.of(authorRequest.getPageNumber(), pageSize, authorRequest.getSort());
    }

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

        final Pageable pageable = getPageable(authorRequest);
        Page<AuthorEntity> results = authorRepository.findAll(example, pageable);

        AuthorResponses authorResponses = new AuthorResponses();

        authorResponses.setCurrentPage(results.getNumber());
        authorResponses.setTotalPages(results.getTotalPages());
        authorResponses.setTotalElements(results.getTotalElements());
        authorResponses.setAuthors(results.stream().map(AuthorMapper::mapAuthorEntityToAuthorResponse).toList());

        return authorResponses;
    }

    public List<AuthorDTO> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return List.of();
        }

        return authorRepository.findByFirstName(firstName);
    }
}
