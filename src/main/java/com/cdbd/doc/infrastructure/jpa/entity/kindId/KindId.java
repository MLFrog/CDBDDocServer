package com.cdbd.doc.infrastructure.jpa.entity.kindId;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
@Data
public class KindId {
	@Column(name = "kind_cd")
	@Comment("분류코드")
	private String kindCd;
	
	@Column(name = "p_kind_cd")
	@Comment("부모분류코드")
	private String pKindCd;
}
