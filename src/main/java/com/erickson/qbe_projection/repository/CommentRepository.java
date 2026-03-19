package com.erickson.qbe_projection.repository;

import com.erickson.qbe_projection.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, QueryByExampleExecutor<CommentEntity> {
}
