package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocVerEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocVerRepository;

@SpringBootTest
public class DocVerRepositoryTest {
	@Autowired
	private DocVerRepository docVerRepository;
	
	private DocVerEntity docVerEntity;
	
	final private String testDocVerId = "001";
	final private String testFileId = "7f06bd96-e11d-4c2a-9b9c-c68b45d8a65e";
	final private String testUserId = "testUser";
	
	@BeforeEach
	public void 엔터티생성() {
		// given
		docVerEntity = new DocVerEntity();
		
		docVerEntity.setDocVerId(testDocVerId);
		docVerEntity.setFileId(testFileId);
		docVerEntity.setUserId(testUserId);
		docVerEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서버전생성하기() {
		// given
		
		// when
		DocVerEntity docVerJpaResultEntity = docVerRepository.save(docVerEntity);
		
		// then
		assertThat(docVerJpaResultEntity.getDocVerId()).isEqualTo(testDocVerId);
		assertThat(docVerJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(docVerJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docVerJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서버전조회하기() {
		// given
		docVerRepository.save(docVerEntity);
		
		// when 
		DocVerEntity docVerJpaResultEntity = docVerRepository.findById(testDocVerId).orElse(null);
		
		// then
		assertThat(docVerJpaResultEntity.getDocVerId()).isEqualTo(testDocVerId);
		assertThat(docVerJpaResultEntity.getFileId()).isEqualTo(testFileId);
		assertThat(docVerJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docVerJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서버전삭제하기() {
		// given
		docVerRepository.save(docVerEntity);
		
		// when
		docVerRepository.deleteById(testDocVerId);
		
		// then 
		DocVerEntity docVerJpaResultEntity = docVerRepository.findById(testDocVerId).orElse(null);
		
		assertThat(docVerJpaResultEntity).isNull();
	}
}
