package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.FileJpaRepository;

@SpringBootTest
public class FileJpaRepositoryTest {

	@Autowired
	private FileJpaRepository fileJpaRepository;
	
	private FileJpaEntity fileJpaEntity;
	
	final private String testFileId = "7f06bd96-e11d-4c2a-9b9c-c68b45d8a65e";
	final private String testFileName = "박희재_계약서";
	final private String testServerFileName = "7f06bd96-e11d-4c2a-9b9c-c68b45d8a65e";
	final private String testServerFilePath = "/2025/02/11/";
	final private String testFileExt = "pdf";
	
	@BeforeEach
	public void 엔터티생성() {
		// given
		fileJpaEntity = new FileJpaEntity();
		
		fileJpaEntity.setFileId(testFileId);
		fileJpaEntity.setFileName(testFileName);
		fileJpaEntity.setServerFileName(testServerFileName);
		fileJpaEntity.setServerFilePath(testServerFilePath);
		fileJpaEntity.setFileExt(testFileExt);
		fileJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 파일생성하기() {
		// given
		
		// when
		FileJpaEntity fileJpaResultEntity = fileJpaRepository.save(fileJpaEntity);
		
		// then
		assertThat(fileJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(fileJpaResultEntity.getFileName()).isEqualTo(testFileName);
		assertThat(fileJpaResultEntity.getServerFileName()).isEqualTo(testServerFileName);
		assertThat(fileJpaResultEntity.getServerFilePath()).isEqualTo(testServerFilePath);
		assertThat(fileJpaResultEntity.getFileExt()).isEqualTo(testFileExt);
		assertThat(fileJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 파일조회하기() {
		// given
		fileJpaRepository.save(fileJpaEntity);
		
		// when
		FileJpaEntity fileJpaResultEntity = fileJpaRepository.findById(testFileId).orElse(null);
		
		// then 
		assertThat(fileJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(fileJpaResultEntity.getFileName()).isEqualTo(testFileName);
		assertThat(fileJpaResultEntity.getServerFileName()).isEqualTo(testServerFileName);
		assertThat(fileJpaResultEntity.getServerFilePath()).isEqualTo(testServerFilePath);
		assertThat(fileJpaResultEntity.getFileExt()).isEqualTo(testFileExt);
		assertThat(fileJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 파일삭제하기() {
		// given
		fileJpaRepository.save(fileJpaEntity);
		
		// when
		fileJpaRepository.delete(fileJpaEntity);
		
		// then
		FileJpaEntity fileJpaResultEntity = fileJpaRepository.findById(testFileId).orElse(null);
		
		assertThat(fileJpaResultEntity).isNull();
	}
}
