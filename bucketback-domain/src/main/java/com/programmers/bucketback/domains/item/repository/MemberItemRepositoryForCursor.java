package com.programmers.bucketback.domains.item.repository;

import java.util.List;

import com.programmers.bucketback.common.model.Hobby;
import com.programmers.bucketback.domains.bucket.model.BucketMemberItemSummary;
import com.programmers.bucketback.domains.item.model.MemberItemSummary;

public interface MemberItemRepositoryForCursor {
	List<BucketMemberItemSummary> findBucketMemberItemsByCursor(
		final List<Long> itemIdsFromBucketItem,
		final List<Long> itemIdsFromMemberItem,
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	);

	List<MemberItemSummary> findMemberItemsByCursor(
		final Hobby hobby,
		final Long memberId,
		final String cursorId,
		final int pageSize
	);
}
