package com.cdbd.doc.application.document;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cdbd.doc.infrastructure.jpa.entity.DocEntity;
import com.cdbd.doc.infrastructure.jpa.entity.FileEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocRepository;
import com.cdbd.doc.infrastructure.jpa.repository.FileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// TODO: AOP를 이용한 GlobalException 으로 처리
class DocumentService
{
	private final DocRepository docRepository;
	
	private final FileRepository fileRepository;
	
	/**
	 * 문서 생성하기
	 * @param docJpaEntity
	 * @param fileJpaEntity
	 * @return
	 * @throws Exception
	 */
	public DocEntity createDocument(DocEntity docJpaEntity, FileEntity fileJpaEntity) {
		// 1. 문서 존재 여부 확인
		Assert.isNull(this.docRepository.findById(docJpaEntity.getDocId()), "해당 문서가 등록되어 있습니다.");
		
		// 2. 문서 등록 
		DocEntity result = this.docRepository.save(docJpaEntity);
		
		// 3. 파일 등록
		this.fileRepository.save(fileJpaEntity);
			
		return result;
	}
	
	/**
	 * 문서 목록 조회하기
	 * @param docName
	 * @param createAt
	 * @return
	 */
	public List<DocEntity> getDocumentList(String docName, Timestamp createAt) {
		return this.docRepository.findAll();
	}
	
	/**
	 * 문서 상세 조회
	 * @param docId
	 * @return
	 */
	public DocEntity getDocument(String docId) {
		Assert.notNull(docId, "해당 ID 값이 없습니다.");
		
		return this.docRepository.findById(docId).orElse(null);
	}
	
	/**
	 * 문서 삭제 하기
	 * @param docId
	 * @throws Exception
	 */
	public void deleteDocument(String docId) {
		// 1. 유효성 검증
		Assert.notNull(docId, "해당 ID 값이 없습니다.");
		
		// 2. 문서 유효성 검증
		Assert.isNull(this.docRepository.findById(docId), "해당 문서가 없습니다."); 
		
		// 3. 문서 삭제
		this.docRepository.deleteById(docId);
		
		// 4. 파일 삭제
	}
}

