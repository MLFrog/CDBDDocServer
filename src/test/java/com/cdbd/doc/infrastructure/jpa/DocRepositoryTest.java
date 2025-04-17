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

import com.cdbd.doc.infrastructure.jpa.entity.DocEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocRepository;
@SpringBootTest
public class DocRepositoryTest{
	@Autowired
	private DocRepository docRepository; 
	
	private DocEntity docEntity;

	final private String testDocId = "81c9ef13-a2c1-4421-bee3-cb0c7c9e3fe4";
	final private String testDocKindCd = "DOC001";
	final private String testDocVersionId = "";
	final private String testUserId = "test01";
	
	@BeforeEach
	public void 엔터티생성() {
		// given
//		docEntity = new docEntity();
		
		docEntity.setDocId(testDocId);
		docEntity.setDocKindCd(testDocKindCd);
		docEntity.setDocVersionId(testDocVersionId);
		docEntity.setUserId(testUserId);
		docEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		docEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서생성하기() {
		// given
		
		// when
		DocEntity docJpaResultEntity = docRepository.save(docEntity);
		
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
		docRepository.save(docEntity);
		
		// when
		DocEntity docJpaResultEntity = docRepository.findById(testDocId).orElse(null);
		
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
		docRepository.save(docEntity);
		
		// when 
		docRepository.deleteById(testDocId);
		
		// then 
		DocEntity docJpaResultEntity = docRepository.findById(testDocId).orElse(null);
		
		assertThat(docJpaResultEntity).isNull();
	}
}
