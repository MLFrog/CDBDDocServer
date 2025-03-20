package com.cdbd.doc.application.document;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdbd.doc.infrastructure.jpa.entity.DocJpaEntity;
import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.DocJpaRepository;
import com.cdbd.doc.infrastructure.jpa.repository.FileJpaRepository;

@Service
public class DocumentService
{
	@Autowired
	DocJpaRepository docJpaRepository;
	
	@Autowired
	FileJpaRepository fileJpaRepository;
	
	// 문서 생성하기 
	public DocJpaEntity createDocument(DocJpaEntity docJpaEntity, FileJpaEntity fileJpaEntity) throws Exception {
		DocJpaEntity result = null;
		try {
			// 1. 문서 존재 여부 확인
			result = docJpaRepository.findById(docJpaEntity.getDocId()).orElse(null);
			
			if (result != null) {
				throw new Exception("이미 존재하는 문서입니다.");
			}
			
			// 2. 문서 등록
			result = docJpaRepository.save(docJpaEntity);
			
			// 3. 파일 등록
			// TODO : 파일 서비스 등록 완료 후 작성 예정
			
			
		} catch (Exception e) {
			throw new Exception("문서 등록 중 오류 : " + e.getMessage());
		}
		return result;
	}
	
	// 문서 목록 조회하기
	public List<DocJpaEntity> getDocumentList(String docId, String docName, Timestamp createAt) {
		List<DocJpaEntity> resultList = docJpaRepository.findAll();
		return resultList;
	}
	
	// 문서 상세 조회
	public DocJpaEntity getDocument(String docId) {
		DocJpaEntity result = docJpaRepository.findById(docId).orElse(null);
		
		return result;
	}
	
	// 문서 삭제 하기
	public void deleteDocument(String docId) throws Exception {
		DocJpaEntity result = null;
		try {
			// 1. 문서 존재 여부 확인
			result = docJpaRepository.findById(docId).orElse(null);
			
			if (result != null) {
				throw new Exception("이미 삭제된 문서입니다.");
			}
			
			// 2. 문서 삭제
			docJpaRepository.deleteById(docId);
			
			// 3. 파일 삭제
		} catch (Exception e) {
			throw new Exception("문서 삭제 중 오류 : " + e.getMessage());
		}
	}
	
}

