package com.cdbd.doc.infrastructure.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.UUID;

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

	@Test
	public void 문서생성하기() {
		// given
		DocJpaEntity docJpaEntity = new DocJpaEntity();
		
		docJpaEntity.setDocId(UUID.randomUUID().toString());
		docJpaEntity.setDocKindCd("");
		docJpaEntity.setDocVersionId("");
		docJpaEntity.setFileExt("pdf");
		docJpaEntity.setFileName("휴가계");
		docJpaEntity.setUserId("test01");
		docJpaEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		docJpaEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		
		// when
		DocJpaEntity savedEntity = docJpaRepository.save(docJpaEntity);
		
		// then
		assertThat(savedEntity.getDocId()).isNotNull();
		assertThat(savedEntity.getDocKindCd()).isEqualTo("");
		assertThat(savedEntity.getDocVersionId()).isEqualTo("");
		assertThat(savedEntity.getFileExt()).isEqualTo("pdf");
		assertThat(savedEntity.getFileName()).isEqualTo("휴가계");
		assertThat(savedEntity.getUserId()).isEqualTo("test01");
		assertThat(savedEntity.getCreatedAt()).isNotNull();
		assertThat(savedEntity.getUpdatedAt()).isNotNull();
	}
	
}
