package com.cdbd.doc.infrastructure.jpa.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "document")
@Comment("문서")
public class DocJpaEntity {
	@Id
	@Column(name = "doc_id")
	@Comment("문서번호")
	private String docId;
	
	@Column(name = "doc_name")
	@Comment("문서명")
	private String docName;
	
	@Column(name = "user_id")
	@Comment("사용자ID")	
	private String userId;
	
	@Column(name = "doc_kind_cd")
	@Comment("문서분류코드")
	private String docKindCd;
	
	@Column(name = "doc_version_id")
	@Comment("문서버전ID")
	private String docVersionId;
	
	@Column(name = "created_at")
	@Comment("생성일시")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@Comment("수정일시")
	private Timestamp updatedAt;
}
