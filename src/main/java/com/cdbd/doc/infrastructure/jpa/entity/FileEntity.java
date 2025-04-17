package com.cdbd.doc.infrastructure.jpa.entity;


import java.sql.Timestamp;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "file")
@Comment("파일")
@Builder
public class FileEntity {
	@Id
	@Column(name = "file_id")
	@Comment("파일ID")
	private String fileId;
	
	@Column(name = "file_name")
	@Comment("파일명")
	private String fileName;
	
	@Column(name = "server_file_name")
	@Comment("서버파일명")
	private String serverFileName;
	
	@Column(name = "server_file_path")
	@Comment("서버파일경로")
	private String serverFilePath;
	
	@Column(name = "file_ext")
	@Comment("파일확장자")
	private String fileExt;
	
	@Column(name = "file_size")
	@Comment("파일크기")
	private long fileSize;
	
	@Column(name = "create_at")
	@Comment("생성일시")
	private Timestamp createdAt;
}
