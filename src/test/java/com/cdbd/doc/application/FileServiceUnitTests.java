package com.cdbd.doc.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cdbd.doc.application.file.FileService;
import com.cdbd.doc.infrastructure.jpa.entity.FileEntity;
import com.cdbd.doc.infrastructure.jpa.repository.FileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class FileServiceUnitTests {

	@InjectMocks
	private FileService fileService;
	
	@Mock
	private FileRepository fileRepository;
	
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
	public void 파일_업로드_실패_유효한_파일명이_아닌_경우() {
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

	@Test
	public void 파일_등록_성공() throws IOException {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"테스트 파일명.txt",
			"text/plain",
			"HELLO".getBytes()
		);
		
		FileEntity expectedFileEntity = FileEntity.builder()
				.fileId("892u39j-asdf892-sadfj89j2")
				.fileName("테스트 파일명")
				.serverFileName("892u39j-asdf892-sadfj89j2")
				.serverFilePath("/home/")
				.fileExt("txt")
				.fileSize(20 * 1024 * 1024)
				.build();
		
		when(this.fileRepository.findById(any())).thenReturn(null);
		when(this.fileRepository.save(any())).thenReturn(expectedFileEntity);
		
		// when
		FileEntity resultEntity = this.fileService.createFile(initialUploadFile);
		
		// then
		assertEquals(resultEntity.getFileId(), "892u39j-asdf892-sadfj89j2");
	}
	
	@Test
	public void 파일_등록_실패_파일ID가_중복인_경우() {
		// given
		MultipartFile initialUploadFile = new MockMultipartFile(
			UUID.randomUUID().toString(), 
			"테스트 파일명.txt",
			"text/plain",
			"HELLO".getBytes()
		);
		
		Optional<FileEntity> expectedFileEntity = Optional.of(FileEntity.builder()
				.fileId("892u39j-asdf892-sadfj89j2")
				.fileName("테스트 파일명")
				.serverFileName("892u39j-asdf892-sadfj89j2")
				.serverFilePath("/home/")
				.fileExt("txt")
				.fileSize(20 * 1024 * 1024)
				.build());
		
		when(this.fileRepository.findById(anyString())).thenReturn(expectedFileEntity);
		
		// when
		
		// then 
		assertThrows(IllegalArgumentException.class, () -> this.fileService.createFile(initialUploadFile));
	}
	
	@Test
	public void 파일_조회_성공() {
		// given
		Optional<FileEntity> expectedFileEntity = Optional.of(FileEntity.builder()
				.fileId("892u39j-asdf892-sadfj89j2")
				.fileName("테스트 파일명")
				.serverFileName("892u39j-asdf892-sadfj89j2")
				.serverFilePath("/home/")
				.fileExt("txt")
				.fileSize(20 * 1024 * 1024)
				.build());
		
		when(this.fileRepository.findById(anyString())).thenReturn(expectedFileEntity);
		
		// when
		FileEntity resultFileEntity = this.fileService.getFile(anyString());
		
		// then
		assertNotNull(resultFileEntity);
		assertEquals(resultFileEntity.getFileId(), "892u39j-asdf892-sadfj89j2");
		assertEquals(resultFileEntity.getFileName(), "테스트 파일명");
		assertEquals(resultFileEntity.getServerFileName(), "892u39j-asdf892-sadfj89j2");
		assertEquals(resultFileEntity.getServerFilePath(), "/home/");
		assertEquals(resultFileEntity.getFileExt(), "txt");
		assertEquals(resultFileEntity.getFileSize(), 20 * 1024 * 1024);
	}
	
	@Test
	public void 파일_조회_실패_파일ID가_없는_경우() {
		String fileId = null;
		
		assertThrows(IllegalArgumentException.class, () -> this.fileService.getFile(fileId));
	}
	
	
	
	
}
