package com.cdbd.doc.infrastructure.jpa.entity;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "doc_kind_cd")
@Comment("문서분류코드")
public class DocKindCdJpaEntity {
	@Id
	@Column(name = "kind_cd")
	@Comment("분류코드")
	private String kindCd;
	
	@Column(name = "p_kind_cd")
	@Comment("부모분류코드")
	private String pKindCd;
	
	@Column(name = "kind_nm")
	@Comment("분류명")
	private String kindNm;
	
	@Column(name = "created_at")
	@Comment("등록일")
	private String createdAt;
}
