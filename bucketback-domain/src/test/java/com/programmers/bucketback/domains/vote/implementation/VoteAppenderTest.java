package com.programmers.bucketback.domains.vote.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.vote.domain.Vote;
import com.programmers.bucketback.domains.vote.domain.VoteBuilder;
import com.programmers.bucketback.domains.vote.model.VoteCreateImplRequest;
import com.programmers.bucketback.domains.vote.model.VoteCreateImplRequestBuilder;
import com.programmers.bucketback.domains.vote.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
class VoteAppenderTest {

	@InjectMocks
	private VoteAppender voteAppender;

	@Mock
	private VoteRepository voteRepository;

	@Test
	@DisplayName("투표를 저장한다.")
	void appendTest() {
		// given
		final Long memberId = 1L;
		final VoteCreateImplRequest request = VoteCreateImplRequestBuilder.build();
		final Vote vote = VoteBuilder.build(memberId);

		given(voteRepository.save(any(Vote.class)))
			.willReturn(vote);

		// when
		final Long savedVoteId = voteAppender.append(memberId, request);

		// then
		assertThat(savedVoteId).isEqualTo(vote.getId());
	}
}