package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.DocKindEntity;

public interface DocKindRepository extends JpaRepository<DocKindEntity, String> {

}
