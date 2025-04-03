package com.cdbd.doc.application.document;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cdbd.doc.infrastructure.jpa.entity.DocJpaEntity;
import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// TODO: AOP를 이용한 GlobalException 으로 처리
class DocumentService
{
	private final DocJpaRepository docJpaRepository;
	
	/**
	 * 문서 생성하기
	 * @param docJpaEntity
	 * @param fileJpaEntity
	 * @return
	 * @throws Exception
	 */
	public DocJpaEntity createDocument(DocJpaEntity docJpaEntity, FileJpaEntity fileJpaEntity) {
		// 1. 문서 존재 여부 확인
		Assert.isNull(this.docJpaRepository.findById(docJpaEntity.getDocId()), "해당 문서가 등록되어 있습니다.");
		
		// 2. 문서 등록 
		DocJpaEntity result = this.docJpaRepository.save(docJpaEntity);
		
		// 3. 파일 등록
		// TODO : 파일 서비스 등록 완료 후 작성 예정
			
		return result;
	}
	
	/**
	 * 문서 목록 조회하기
	 * @param docId
	 * @param docName
	 * @param createAt
	 * @return
	 */
	public List<DocJpaEntity> getDocumentList(String docId, String docName, Timestamp createAt) {
		return this.docJpaRepository.findAll();
	}
	
	/**
	 * 문서 상세 조회
	 * @param docId
	 * @return
	 */
	public DocJpaEntity getDocument(String docId) {
		Assert.notNull(docId, "ID 값이 없습니다.");
		
		return this.docJpaRepository.findById(docId).orElse(null);
	}
	
	/**
	 * 문서 삭제 하기
	 * @param docId
	 * @throws Exception
	 */
	public void deleteDocument(String docId) {
		// 1. 유효성 검증
		Assert.notNull(docId, "ID 값이 없습니다.");
		
		// 2. 문서 유효성 검증
		Assert.isNull(this.docJpaRepository.findById(docId), "해당 문서가 없습니다."); 
		
		// 3. 문서 삭제
		this.docJpaRepository.deleteById(docId);
		
		// 4. 파일 삭제
	}
}

