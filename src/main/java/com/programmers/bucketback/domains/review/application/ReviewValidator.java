package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.review.domain.Review;
import com.programmers.bucketback.global.error.exception.BusinessException;
import com.programmers.bucketback.global.error.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

	private final ReviewReader reviewReader;

	public void validateReviewComeFromItem(
		final Long itemId,
		final Long reviewId
	) {
		Review review = reviewReader.read(reviewId);
		if (!review.containsItem(itemId)) {
			throw new BusinessException(ErrorCode.REVIEW_NOT_EQUAL_ITEM);
		}
	}

	public void validateMemberIsReviewOwner(
		Long reviewId,
		Long memberId
	) {
		Review review = reviewReader.read(reviewId);
		if (!review.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.REVIEW_NOT_MINE);
		}
	}
}
