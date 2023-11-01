package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.domains.common.MemberUtils;
import com.programmers.bucketback.domains.review.application.dto.ReviewContent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewAppender reviewAppender;
	private final ReviewModifier reviewModifier;
	private final ReviewValidator reviewValidator;
	private final ReviewRemover reviewRemover;

	public void createReview(
		final Long itemId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewAppender.append(itemId, memberId, reviewContent);
	}

	public void updateReview(
		final Long itemId,
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validateReviewComeFromItem(itemId, reviewId);
		reviewValidator.validateMemberIsReviewOwner(reviewId, memberId);
		reviewModifier.modify(reviewId, reviewContent);
	}

	public void deleteReview(
		final Long itemId,
		final Long reviewId
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validateReviewComeFromItem(itemId, reviewId);
		reviewValidator.validateMemberIsReviewOwner(reviewId, memberId);
		reviewRemover.remove(reviewId);
	}
}
