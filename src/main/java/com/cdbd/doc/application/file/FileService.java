package com.cdbd.doc.application.file;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.cdbd.doc.infrastructure.jpa.entity.FileJpaEntity;
import com.cdbd.doc.infrastructure.jpa.repository.FileJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileJpaRepository fileJpaRepository;
	
	private final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB 
	private static final String[] ACCEPTED_EXT = {
	    "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt"
	};
	
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
	
	/**
	 * 파일 업로드
	 * @param uploadFile
	 * @return
	 */
	public FileJpaEntity uploadFile(MultipartFile uploadFile) {
		// 1. 파일 유효성 체크
		checkFileValidation(uploadFile);
		
		
		long fileSize = uploadFile.getSize();
		
		String fileName = extractFileName(uploadFile);
		String fileExt = extractFileExt(uploadFile);
		
		// 2. 파일 서버 저장 경로 생성
		String serverFileName = UUID.randomUUID().toString();
		String serverPath = createUploadServerPath();

		// 3. 파일 업로드
//			try {
//				uploadFile.transferTo(new File(serverPath + File.seperator + serverFileName));
//			} catch (IOException ioe) {
//				
//			}
		
		// 4. 파일 정보 return
		return FileJpaEntity.builder()
							.fileId(serverFileName)
							.fileName(fileName)
							.serverFileName(serverFileName)
							.serverFilePath(serverPath)
							.fileExt(fileExt)
							.fileSize(fileSize)
							.createdAt(new Timestamp(System.currentTimeMillis()))
							.build();
	}
	
	public void checkFileValidation(MultipartFile uploadFile) {
		Assert.notNull(uploadFile, "해당 파일이 없습니다");
		Assert.isTrue(!uploadFile.getOriginalFilename().isBlank(), "해당 파일명이 없습니다.");
		Assert.isTrue(uploadFile.getSize() <= MAX_FILE_SIZE , "업로드 파일 최대 크기를 초과했습니다.");
		Assert.isTrue(Arrays.asList(ACCEPTED_EXT).contains(extractFileExt(uploadFile)), "허용된 파일 확장자가 아닙니다.");
	}
	
	public String extractFileExt(MultipartFile uploadFile) {
		String orgFileName = uploadFile.getOriginalFilename();
		
		int delimIndex = orgFileName.lastIndexOf(".");
		String fileExt = orgFileName.substring(delimIndex + 1);
		
		return fileExt;
	}
	
	public String extractFileName(MultipartFile uploadFile) {
		String orgFileName = uploadFile.getOriginalFilename().substring(0);
		
		int delimIndex = orgFileName.lastIndexOf(".");
		String fileName = orgFileName.substring(0, delimIndex);
		
		return fileName;
	}
	
	public String createUploadServerPath() {
		LocalDate now = LocalDate.now();
		
		// 2. 파일 서버 저장 경로 생성
		String path = String.format("%d"+ File.separator + "%s" + File.separator + "%02d"
			,now.getYear()
			,now.getMonth()
			,now.getDayOfMonth()
		); 
		return path;
	}
}
