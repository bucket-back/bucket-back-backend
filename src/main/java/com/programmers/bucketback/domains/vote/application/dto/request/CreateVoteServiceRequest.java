package com.programmers.bucketback.domains.vote.application.dto.request;

import com.programmers.bucketback.domains.common.Hobby;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateVoteServiceRequest(
	@NotNull
	Hobby hobby,

	@NotNull
	String content,

	@NotNull
	Long option1ItemId,

	@NotNull
	Long option2ItemId
) {
}
