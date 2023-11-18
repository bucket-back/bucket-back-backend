package com.programmers.bucketback.domains.review.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.common.cursor.CursorSummary;
import com.programmers.bucketback.domains.review.application.dto.ReviewGetByCursorServiceResponse;
import com.programmers.bucketback.domains.review.implementation.ReviewAppender;
import com.programmers.bucketback.domains.review.implementation.ReviewCursorReader;
import com.programmers.bucketback.domains.review.implementation.ReviewModifier;
import com.programmers.bucketback.domains.review.implementation.ReviewRemover;
import com.programmers.bucketback.domains.review.implementation.ReviewStatistics;
import com.programmers.bucketback.domains.review.model.ReviewContent;
import com.programmers.bucketback.domains.review.model.ReviewCursorSummary;
import com.programmers.bucketback.global.level.PayPoint;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewAppender reviewAppender;
	private final ReviewModifier reviewModifier;
	private final ReviewValidator reviewValidator;
	private final ReviewCursorReader reviewCursorReader;
	private final ReviewRemover reviewRemover;
	private final ReviewStatistics reviewStatistics;

	@PayPoint(15)
	public Long createReview(
		final Long itemId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewAppender.append(itemId, memberId, reviewContent);

		return memberId;
	}

	public void updateReview(
		final Long itemId,
		final Long reviewId,
		final ReviewContent reviewContent
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewModifier.modify(reviewId, reviewContent);
	}

	public ReviewGetByCursorServiceResponse getReviewsByCursor(
		final Long itemId,
		final CursorPageParameters parameters
	) {
		Long reviewCount = reviewStatistics.getReviewCount(itemId);
		CursorSummary<ReviewCursorSummary> cursorSummary = reviewCursorReader.readByCursor(itemId, parameters);

		return new ReviewGetByCursorServiceResponse(reviewCount, cursorSummary);
	}

	public void deleteReview(
		final Long itemId,
		final Long reviewId
	) {
		Long memberId = MemberUtils.getCurrentMemberId();
		reviewValidator.validItemReview(itemId, reviewId);
		reviewValidator.validOwner(reviewId, memberId);
		reviewRemover.remove(reviewId);
	}
}