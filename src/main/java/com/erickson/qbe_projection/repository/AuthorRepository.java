package com.erickson.qbe_projection.repository;

import com.erickson.qbe_projection.dto.AuthorDTO;
import com.erickson.qbe_projection.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long>, QueryByExampleExecutor<AuthorEntity> {
    List<AuthorDTO> findByFirstName(String firstName);
}
