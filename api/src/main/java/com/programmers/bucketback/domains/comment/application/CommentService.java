package com.programmers.bucketback.domains.comment.application;

import org.springframework.stereotype.Service;

import com.programmers.bucketback.common.cursor.CursorPageParameters;
import com.programmers.bucketback.domains.comment.api.dto.response.CommentGetCursorResponse;
import com.programmers.bucketback.domains.comment.application.model.CommentCursorSummary;
import com.programmers.bucketback.domains.comment.domain.Comment;
import com.programmers.bucketback.domains.comment.implementation.CommentAppender;
import com.programmers.bucketback.domains.comment.implementation.CommentModifier;
import com.programmers.bucketback.domains.comment.implementation.CommentReader;
import com.programmers.bucketback.domains.comment.implementation.CommentRemover;
import com.programmers.bucketback.domains.feed.domain.Feed;
import com.programmers.bucketback.domains.feed.implementation.FeedReader;
import com.programmers.bucketback.error.exception.BusinessException;
import com.programmers.bucketback.error.exception.ErrorCode;
import com.programmers.bucketback.global.level.PayPoint;
import com.programmers.bucketback.global.util.MemberUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentAppender commentAppender;
	private final CommentValidator commentValidator;
	private final CommentModifier commentModifier;
	private final CommentRemover commentRemover;
	private final CommentReader commentReader;
	private final FeedReader feedReader;

	@PayPoint(5)
	public Long createComment(
		final Long feedId,
		final String content
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		commentAppender.append(feedId, content, memberId);

		return memberId;
	}

	public void modifyComment(
		final Long feedId,
		final Long commentId,
		final String content
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentModifier.modify(commentId, content);
	}

	public void deleteComment(
		final Long feedId,
		final Long commentId
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		commentValidator.validCommentInFeed(feedId, commentId);
		commentValidator.validCommentOwner(commentId, memberId);
		commentRemover.remove(commentId);
	}

	public CommentGetCursorResponse getFeedComments(
		final Long feedId,
		final CursorPageParameters parameters
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();
		final CommentCursorSummary commentCursorSummary = commentReader.readByCursor(feedId, memberId, parameters);

		return new CommentGetCursorResponse(commentCursorSummary);
	}

	@PayPoint(20)
	public Long adoptComment(
		final Long feedId,
		final Long commentId
	) {
		final Long memberId = MemberUtils.getCurrentMemberId();

		final Feed feed = feedReader.read(feedId);
		if (!feed.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.FORBIDDEN);
		}

		final Comment comment = commentReader.read(commentId);
		if (!comment.isInFeed(feedId)) {
			throw new BusinessException(ErrorCode.COMMENT_NOT_IN_FEED);
		}

		if (comment.isOwner(memberId)) {
			throw new BusinessException(ErrorCode.COMMENT_CANNOT_ADOPT);
		}

		commentModifier.adopt(comment);

		return comment.getMemberId();
	}
}