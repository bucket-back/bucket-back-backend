package com.programmers.bucketback.domains.vote.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.vote.api.dto.request.VoteCreateRequest;
import com.programmers.bucketback.domains.vote.application.VoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

	private final VoteService voteService;

	@PostMapping
	public ResponseEntity<Void> createVote(@Valid @RequestBody final VoteCreateRequest request) {
		final Long voteId = voteService.createVote(request.toCreateVoteServiceRequest());

		return ResponseEntity.created(URI.create("http://localhost:8080/api/votes/" + voteId)).build();
	}
}
