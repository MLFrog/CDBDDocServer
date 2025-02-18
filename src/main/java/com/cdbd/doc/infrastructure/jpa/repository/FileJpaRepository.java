package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, String> {

}
