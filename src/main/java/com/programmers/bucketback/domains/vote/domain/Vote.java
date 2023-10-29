package com.programmers.bucketback.domains.vote.domain;

import java.time.LocalDateTime;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.item.domain.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option1_item_id")
	private Item option1Item;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option2_item_id")
	private Item option2Item;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@NotNull
	@Column(name = "content")
	private String content;

	@NotNull
	@Column(name = "start_time")
	private LocalDateTime startTime;

	@NotNull
	@Column(name = "end_time")
	private LocalDateTime endTime;
}
