package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.domains.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewRemover {

	private final ReviewReader reviewReader;
	private final ReviewRepository reviewRepository;

	public void remove(
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);
		reviewRepository.delete(review);
	}
}
