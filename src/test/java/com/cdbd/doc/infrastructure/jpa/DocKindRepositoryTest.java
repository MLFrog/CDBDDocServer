package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocKindEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocKindRepository;

@SpringBootTest
public class DocKindRepositoryTest {

	@Autowired
	private DocKindRepository docKindRepository;
	
	private DocKindEntity docKindEntity;
	
	final private String testDocKindCd = "DOC001";
	final private String testPDocKindCd = null;
	final private String testDocKindNm = "계약서";
	
	@BeforeEach
	public void 엔터티생성() {
		docKindEntity = new DocKindEntity();
		
		docKindEntity.setDocKindCd(testDocKindCd);
		docKindEntity.setPDocKindCd(testPDocKindCd);
		docKindEntity.setDocKindNm(testDocKindNm);
		docKindEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	@Test
	public void 문서종류생성하기() {
		// given
		
		// when
		DocKindEntity docKindJpaResultEntity = docKindRepository.save(docKindEntity);
		
		/// then
		assertThat(docKindJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docKindJpaResultEntity.getPDocKindCd()).isEqualTo(testPDocKindCd);
		assertThat(docKindJpaResultEntity.getDocKindNm()).isEqualTo(testDocKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류조회하기() {
		// given
		docKindRepository.save(docKindEntity);
		
		// when
		DocKindEntity docKindJpaResultEntity = docKindRepository.findById(testDocKindCd).orElse(null);
		
		assertThat(docKindJpaResultEntity.getDocKindCd()).isEqualTo(testDocKindCd);
		assertThat(docKindJpaResultEntity.getPDocKindCd()).isEqualTo(testPDocKindCd);
		assertThat(docKindJpaResultEntity.getDocKindNm()).isEqualTo(testDocKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류삭제하기() {
		// given
		docKindRepository.save(docKindEntity);
		
		// when
		docKindRepository.deleteById(testDocKindCd);
		
		// then
		DocKindEntity docKindJpaResultEntity = docKindRepository.findById(testDocKindCd).orElse(null);
		
		assertThat(docKindJpaResultEntity).isNull();
	}
}
