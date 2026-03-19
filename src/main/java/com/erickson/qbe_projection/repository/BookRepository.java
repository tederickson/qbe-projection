package com.erickson.qbe_projection.repository;

import com.erickson.qbe_projection.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long>, QueryByExampleExecutor<BookEntity> {
}
