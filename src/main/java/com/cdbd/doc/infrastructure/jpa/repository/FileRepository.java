package com.cdbd.doc.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cdbd.doc.infrastructure.jpa.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, String> {

}
