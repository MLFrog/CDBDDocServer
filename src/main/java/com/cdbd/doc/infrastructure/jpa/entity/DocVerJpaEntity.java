package com.cdbd.doc.infrastructure.jpa.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "document_version")
@Comment("문서버전")
public class DocVerJpaEntity {
	@Id
	@Column(name = "doc_ver_id")
	@Comment("문서버전ID")
	private String docVerId;
	
	@Column(name = "doc_id")
	@Comment("문서번호")
	private String docId;
	
	@Column(name = "created_at")
	@Comment("등록일")
	private Timestamp createdAt;
}
