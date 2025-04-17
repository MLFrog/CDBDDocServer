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
@Table(name = "doc_kind")
@Comment("문서분류")
public class DocKindEntity {
	@Id
	@Column(name = "doc_kind_cd")
	@Comment("문서분류코드")
	private String docKindCd;
	
	@Column(name = "p_doc_kind_cd")
	@Comment("부모문서분류코드")
	private String pDocKindCd;
	
	@Column(name = "kind_nm")
	@Comment("분류명")
	private String docKindNm;
	
	@Column(name = "created_at")
	@Comment("등록일")
	private Timestamp createdAt;
}
