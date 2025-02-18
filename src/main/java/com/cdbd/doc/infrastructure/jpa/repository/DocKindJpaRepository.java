package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.DocKindJpaEntity;

public interface DocKindJpaRepository extends JpaRepository<DocKindJpaEntity, String> {

}
