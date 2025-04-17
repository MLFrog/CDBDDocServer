package com.cdbd.doc.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cdbd.doc.application.file.FileService;
import com.cdbd.doc.infrastructure.jpa.entity.FileEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class FileServiceUnitTests {
	
	@InjectMocks
	private FileService fileService;
	
	@Test
	public void 파일_업로드_성공() throws IOException {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"abcd.txt",
			"text/plain",
			"HELLO".getBytes()
		);
		
		// when
		FileEntity fileEntity = this.fileService.uploadFile(initialUploadFile);
		
		// then 
		assertThat(fileEntity.getFileId()).isNotNull();
		assertThat(fileEntity.getFileName()).isEqualTo("abcd");
		assertThat(fileEntity.getFileExt()).isEqualTo("txt");
	}
	
	@Test
	public void 파일_업로드_실패_파일이_없는_경우() {
		// given
		MultipartFile initialUploadFile = null;
		// when 
		
		// then 
		assertThrows(IllegalArgumentException.class, () -> this.fileService.uploadFile(initialUploadFile));
	}
	
	@Test
	public void 파일_업로드_실패_파일명이_없는_경우() {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			null,
			"text/plain",
			"HELLO".getBytes()
		);
		
		// when
		
		// then
		assertThrows(IllegalArgumentException.class, () -> this.fileService.uploadFile(initialUploadFile));
	}
	
	@Test
	public void 파일_업로드_실패_지정된_파일_사이즈를_넘을경우() {
		// given
		MultipartFile initialUploadFile1 = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"hellot.txt",
			"text/plain",
			new byte[(int) 21 * 1024 * 1024] // 21MB
		);
		
		// when
		
		// then
		assertThrows(IllegalArgumentException.class, () -> this.fileService.uploadFile(initialUploadFile1));
	}

	@Test
	public void 파일_업로드_실패_지정된_파일_형식이_아닌_경우() {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"hellot.exe",
			"text/plain",
			"HELLO".getBytes()
		);
		// when
		
		// then 
		assertThrows(IllegalArgumentException.class, () -> this.fileService.uploadFile(initialUploadFile));
	}
	
	@Test
	public void 파일_업로드_실패_파일_전송_실패인경우() {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"hellot?.txt",
			"text/plain",
			"HELLO".getBytes()
		);
		// when
		
		// then
		assertThrows(IllegalArgumentException.class, () -> this.fileService.uploadFile(initialUploadFile));
	}
}
