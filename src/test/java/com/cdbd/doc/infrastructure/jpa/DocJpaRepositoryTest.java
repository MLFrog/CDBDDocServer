package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocJpaRepository;
@SpringBootTest
public class DocJpaRepositoryTest{
	@Autowired
	private DocJpaRepository docJpaRepository; 
	
	private DocJpaEntity docJpaEntity;

	final private String testDocId = "81c9ef13-a2c1-4421-bee3-cb0c7c9e3fe4";
	final private String testDocKindCd = "DOC001";
	final private String testDocVersionId = "";
	final private String testUserId = "test01";
	
	@BeforeEach
	public void 엔터티생성() {
		// given
		docJpaEntity = new DocJpaEntity();
		
		docJpaEntity.setDocId(testDocId);
		docJpaEntity.setDocKindCd(testDocKindCd);
		docJpaEntity.setDocVersionId(testDocVersionId);
		docJpaEntity.setUserId(testUserId);
		docJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		docJpaEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서생성하기() {
		// given
		
		// when
		DocJpaEntity docJpaResultEntity = docJpaRepository.save(docJpaEntity);
		
		// then
		assertThat(docJpaResultEntity.getDocId()).isNotNull();
		assertThat(docJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docJpaResultEntity.getDocVersionId()).isEqualTo(testDocVersionId);
		assertThat(docJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docJpaResultEntity.getUpdatedAt()).isNotNull();
		assertThat(docJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서조회하기() {
		// given
		docJpaRepository.save(docJpaEntity);
		
		// when
		DocJpaEntity docJpaResultEntity = docJpaRepository.findById(testDocId).orElse(null);
		
		// then
		assertThat(docJpaResultEntity.getDocId()).isEqualTo(testDocId);
		assertThat(docJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docJpaResultEntity.getDocVersionId()).isEqualTo(testDocVersionId);
		assertThat(docJpaResultEntity.getUserId()).isEqualTo(testUserId);
		assertThat(docJpaResultEntity.getUpdatedAt()).isNotNull();
		assertThat(docJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서삭제하기() {
		// given 
		docJpaRepository.save(docJpaEntity);
		
		// when 
		docJpaRepository.deleteById(testDocId);
		
		// then 
		DocJpaEntity docJpaResultEntity = docJpaRepository.findById(testDocId).orElse(null);
		
		assertThat(docJpaResultEntity).isNull();
	}
}
