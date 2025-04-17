package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.DocVerEntity;

public interface DocVerRepository extends JpaRepository<DocVerEntity, String> {

}
