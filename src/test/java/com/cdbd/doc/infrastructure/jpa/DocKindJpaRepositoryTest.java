package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cdbd.doc.infrastructure.jpa.entity.DocKindJpaEntity;
import com.cdbd.doc.infrastructure.jpa.entity.kindId.KindId;
import com.cdbd.doc.infrastructure.jpa.repository.DocKindJpaRepository;

@SpringBootTest
public class DocKindJpaRepositoryTest {

	@Autowired
	private DocKindJpaRepository docKindJpaRepository;
	
	private DocKindJpaEntity docKindJpaEntity;
	
	final private String testKindCd = "DOC001";
	final private String testPKindCd = "0";
	final private String testKindNm = "계약서";
	
	@BeforeEach
	public void 엔터티생성() {
		KindId kindId = 복합키생성하기();
		
		docKindJpaEntity = new DocKindJpaEntity();
		
		docKindJpaEntity.setKindId(kindId);
		docKindJpaEntity.setKindNm(testKindNm);
		docKindJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
	
	public KindId 복합키생성하기() {
		KindId kindId = new KindId();
		
		kindId.setKindCd(testKindCd);
		kindId.setPKindCd(testPKindCd);
		
		return kindId;
	}
	
	@Test
	public void 문서종류생성하기() {
		// given
		
		// when
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.save(docKindJpaEntity);
		
		/// then
		assertThat(docKindJpaResultEntity.getKindId().getKindCd()).isEqualTo(testKindCd);
		assertThat(docKindJpaResultEntity.getKindId().getPKindCd()).isEqualTo(testPKindCd);
		assertThat(docKindJpaResultEntity.getKindNm()).isEqualTo(testKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류조회하기() {
		// given
		docKindJpaRepository.save(docKindJpaEntity);
		
		// when
		KindId kindId = 복합키생성하기();
		
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.findById(kindId).orElse(docKindJpaEntity);
		
		assertThat(docKindJpaResultEntity.getKindId().getKindCd()).isEqualTo(testKindCd);
		assertThat(docKindJpaResultEntity.getKindId().getPKindCd()).isEqualTo(testPKindCd);
		assertThat(docKindJpaResultEntity.getKindNm()).isEqualTo(testKindNm);
		assertThat(docKindJpaResultEntity.getCreatedAt()).isNotNull();
	}
	
	@Test
	public void 문서종류삭제하기() {
		// given
		docKindJpaRepository.save(docKindJpaEntity);
		
		// when
		KindId kindId = 복합키생성하기();
		
		docKindJpaRepository.deleteById(kindId);
		
		// then
		DocKindJpaEntity docKindJpaResultEntity = docKindJpaRepository.findById(kindId).orElse(docKindJpaEntity);
		
		assertThat(docKindJpaResultEntity).isNotNull();
	}
}
