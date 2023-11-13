package com.programmers.bucketback.domains.item.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.common.vo.CursorPageParameters;
import com.programmers.bucketback.domains.item.application.dto.ItemGetByCursorServiceResponse;
import com.programmers.bucketback.domains.item.application.vo.ItemCursorSummary;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemCursorReader {

	private final ItemRepository itemRepository;

	public ItemGetByCursorServiceResponse readByCursor(
		final String keyword,
		final CursorPageParameters parameters
	) {
		final String trimmedKeyword = keyword.trim();
		if (trimmedKeyword.isEmpty()) {
			return new ItemGetByCursorServiceResponse(null, Collections.emptyList());
		}
		int pageSize = getPageSize(parameters);

		List<ItemCursorSummary> itemCursorSummaries = itemRepository.findAllByCursor(
			trimmedKeyword,
			parameters.cursorId(),
			pageSize
		);

		String nextCursorId = getNextCursorId(itemCursorSummaries);

		return new ItemGetByCursorServiceResponse(
			nextCursorId,
			itemCursorSummaries
		);
	}

	private String getNextCursorId(final List<ItemCursorSummary> itemCursorSummaries) {
		int size = itemCursorSummaries.size();
		if (size == 0) {
			return null;
		}

		ItemCursorSummary lastElement = itemCursorSummaries.get(size - 1);

		return lastElement.cursorId();
	}

	private int getPageSize(final CursorPageParameters parameters) {
		int pageSize = parameters.size();
		if (pageSize == 0) {
			return 20;
		}

		return pageSize;
	}
}
