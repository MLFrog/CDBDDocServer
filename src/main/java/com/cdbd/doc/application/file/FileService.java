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

import com.cdbd.doc.infrastructure.jpa.entity.FileEntity;
import com.cdbd.doc.infrastructure.jpa.repository.FileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class FileService {

	private final FileRepository fileRepository;
	
	private final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB 
	private final String[] ACCEPTED_EXT = {
	    "jpg", "jpeg", "png", "gif", "pdf", "doc", "docx", "xls", "xlsx", "txt"
	};
	private final String rootPath = "/Users/hee/Documents/TEMP";
	private final String invalidRegex = "^[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$";
	
	public FileEntity createFile(MultipartFile uploadFile) throws IOException {
		// 1. 파일 업로드
		FileEntity fileEntity = uploadFile(uploadFile);
		
		// 2. 파일 존재 여부 확인 
		checkFileEntityValidation(fileEntity);
		
		// 3. 파일 등록
		return this.fileRepository.save(fileEntity);
	}
	
	public List<FileEntity> getFileList(String fileId) {
		return this.fileRepository.findAll();
	
	}
	public FileEntity getFile(String fileId) {
		Assert.notNull(fileId, "해당 ID 값이 없습니다.");
		
		return this.fileRepository.findById(fileId).orElse(null);
	}
	
	public void deleteFile(String fileId) {
		Assert.notNull(fileId, "해당 ID 값이 없습니다.");
	
		this.fileRepository.deleteById(fileId);
	}
	
	/**
	 * 파일 업로드
	 * @param uploadFile
	 * @return
	 * @throws IOException 
	 */
	public FileEntity uploadFile(MultipartFile uploadFile) throws IOException {
		// 1. 파일 유효성 체크
		checkUploadFileValidation(uploadFile);
		
		
		long fileSize = uploadFile.getSize();
		
		String fileName = extractFileName(uploadFile);
		String fileExt = extractFileExt(uploadFile);
		
		// 2. 파일 서버 저장 경로 생성
		String serverFileName = UUID.randomUUID().toString();
		String serverPath = createUploadServerPath();
		
		// 3. 파일 업로드
		try {
			// 폴더 생성
			File folder = new File(serverPath);
			
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
			uploadFile.transferTo(new File(serverPath + File.separator + serverFileName));
		} catch (IOException ioe) {
			throw new IOException("파일 업로드에 실패했습니다.");
		}
		
		// 4. 파일 정보 return
		return FileEntity.builder()
				.fileId(serverFileName)
				.fileName(fileName)
				.serverFileName(serverFileName)
				.serverFilePath(serverPath)
				.fileExt(fileExt)
				.fileSize(fileSize)
				.createdAt(new Timestamp(System.currentTimeMillis()))
				.build();
	}
	
	public void checkFileEntityValidation(FileEntity fileEntity) {
		Assert.isNull(this.fileRepository.findById(fileEntity.getFileId()), "해당 파일이 등록되어 있습니다.");
	}
	
	public void checkUploadFileValidation(MultipartFile uploadFile) {
		Assert.notNull(uploadFile, "해당 파일이 없습니다");
		Assert.isTrue(!uploadFile.getOriginalFilename().isBlank(), "해당 파일명이 없습니다.");
		Assert.isTrue(uploadFile.getSize() <= MAX_FILE_SIZE , "업로드 파일 최대 크기를 초과했습니다.");
		Assert.isTrue(Arrays.asList(ACCEPTED_EXT).contains(extractFileExt(uploadFile)), "허용된 파일 확장자가 아닙니다.");
		Assert.isTrue(isValidateFileName(uploadFile.getOriginalFilename()), "유효한 파일명이 아닙니다.");
	}
	
	public String extractFileExt(MultipartFile uploadFile) {
		String orgFileName = uploadFile.getOriginalFilename();
		
		int delimIndex = orgFileName.lastIndexOf(".");
		String fileExt = orgFileName.substring(delimIndex + 1);
		
		return fileExt;
	}
	
	public String extractFileName(MultipartFile uploadFile) {
		String orgFileName = uploadFile.getOriginalFilename();
		
		int delimIndex = orgFileName.lastIndexOf(".");
		String fileName = orgFileName.substring(0, delimIndex);
		
		return fileName;
	}
	
	public String createUploadServerPath() {
		LocalDate now = LocalDate.now();
		
		String path = String.format("%s" + File.separator + "%d" + File.separator + "%02d" + File.separator + "%02d"
				,rootPath
				,now.getYear()
				,now.getMonthValue()
				,now.getDayOfMonth()
		); 
		return path;
	}
	
	public boolean isValidateFileName(String fileName) {
		return fileName.matches(invalidRegex);
	}
}
