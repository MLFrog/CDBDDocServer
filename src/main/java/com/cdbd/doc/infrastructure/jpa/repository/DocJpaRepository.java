package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.DocJpaEntity;

public interface DocJpaRepository extends JpaRepository<DocJpaEntity, String> {

}
