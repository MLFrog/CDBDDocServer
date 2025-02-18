package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocKindJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocKindJpaRepository;

@SpringBootTest
public class DocKindJpaRepositoryTest {

	@Autowired
	private DocKindJpaRepository docKindJpaRepository;
	
	private DocKindJpaEntity docKindJpaEntity;
	
	final private String testDocKindCd = "DOC001";
	final private String testPDocKindCd = "0";
	final private String testDocKindNm = "계약서";
	
	@BeforeEach
	public void 엔터티생성() {
		docKindJpaEntity = new DocKindJpaEntity();
		
		docKindJpaEntity.setDocKindCd(testDocKindCd);
		docKindJpaEntity.setPDocKindCd(testPDocKindCd);
		docKindJpaEntity.setDocKindNm(testDocKindNm);
		docKindJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서종류생성하기() {
		// given
		
		// when
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.save(docKindJpaEntity);
		
		/// then
		assertThat(docKindJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docKindJpaResultEntity.getPDocKindCd()).isEqualTo(testPDocKindCd);
		assertThat(docKindJpaResultEntity.getDocKindNm()).isEqualTo(testDocKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류조회하기() {
		// given
		docKindJpaRepository.save(docKindJpaEntity);
		
		// when
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.findById(testDocKindCd).orElse(null);
		
		assertThat(docKindJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docKindJpaResultEntity.getPDocKindCd()).isEqualTo(testPDocKindCd);
		assertThat(docKindJpaResultEntity.getDocKindNm()).isEqualTo(testDocKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류삭제하기() {
		// given
		docKindJpaRepository.save(docKindJpaEntity);
		
		// when
		docKindJpaRepository.deleteById(testDocKindCd);
		
		// then
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.findById(testDocKindCd).orElse(null);
		
		assertThat(docKindJpaResultEntity).isNotNull();
	}
}
