package com.cdbd.doc.application.file;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.FileJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileJpaRepository fileJpaRepository;
	
	public FileJpaEntity createFile(FileJpaEntity fileJpaEntity) {
		// 1. 파일 존재 여부 확인 
		Assert.isNull(this.fileJpaRepository.findById(fileJpaEntity.getFileId()), "해당 파일이 등록되어 있습니다.");
		
		// 2. 파일 업로드
		// TODO: 파일 업로드 서비스 단에서 종속(?)되어야 하는지?
		// 		이미 문서 등록 시 -> 파일 등록 형식
		// 분리 되어야 하는지?
		
		// 3. 파일 등록
		return this.fileJpaRepository.save(fileJpaEntity);
	}
	
	public List<FileJpaEntity> getFileList(String fileId) {
		// TODO : 파일명, 생성일자를 기준으로 조회 하는 법
		return this.fileJpaRepository.findAll();
	}
	
	public FileJpaEntity getFile(String fileId) {
		Assert.notNull(fileId, "해당 ID 값이 없습니다.");
		
		return this.fileJpaRepository.findById(fileId).orElse(null);
	}
	
	public void deleteFile(String fileId) {
		Assert.notNull(fileId, "해당 ID 값이 없습니다.");
	
		this.fileJpaRepository.deleteById(fileId);
	}
}
