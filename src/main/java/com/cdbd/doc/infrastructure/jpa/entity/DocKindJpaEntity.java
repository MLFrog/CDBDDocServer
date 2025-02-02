package com.cdbd.doc.infrastructure.jpa.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Comment;

import com.cdbd.doc.infrastructure.jpa.entity.kindId.KindId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "doc_kind")
@Comment("문서분류")
public class DocKindJpaEntity {
	@EmbeddedId
	private KindId kindId;
	
	@Column(name = "kind_nm")
	@Comment("분류명")
	private String kindNm;
	
	@Column(name = "created_at")
	@Comment("등록일")
	private Timestamp createdAt;
}
