package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocVerJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocVerJpaRepository;

@SpringBootTest
public class DocVerJpaRepositoryTest {
	@Autowired
	private DocVerJpaRepository docVerJpaRepository;
	
	private DocVerJpaEntity docVerJpaEntity;
	
	final private String testDocVerId = "001";
	final private String testFileId = "7f06bd96-e11d-4c2a-9b9c-c68b45d8a65e";
	final private String testUserId = "testUser";
	
	@BeforeEach
	public void 엔터티생성() {
		// given
		docVerJpaEntity = new DocVerJpaEntity();
		
		docVerJpaEntity.setDocVerId(testDocVerId);
		docVerJpaEntity.setFileId(testFileId);
		docVerJpaEntity.setUserId(testUserId);
		docVerJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서버전생성하기() {
		// given
		
		// when
		DocVerJpaEntity docVerJpaResultEntity = docVerJpaRepository.save(docVerJpaEntity);
		
		// then
		assertThat(docVerJpaResultEntity.getDocVerId()).isEqualTo(testDocVerId);
		assertThat(docVerJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(docVerJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docVerJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서버전조회하기() {
		// given
		docVerJpaRepository.save(docVerJpaEntity);
		
		// when 
		DocVerJpaEntity docVerJpaResultEntity = docVerJpaRepository.findById(testDocVerId).orElse(null);
		
		// then
		assertThat(docVerJpaResultEntity.getDocVerId()).isEqualTo(testDocVerId);
		assertThat(docVerJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(docVerJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docVerJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서버전삭제하기() {
		// given
		docVerJpaRepository.save(docVerJpaEntity);
		
		// when
		docVerJpaRepository.deleteById(testDocVerId);
		
		// then 
		DocVerJpaEntity docVerJpaResultEntity = docVerJpaRepository.findById(testDocVerId).orElse(null);
		
		assertThat(docVerJpaResultEntity).isNull();
	}
}
