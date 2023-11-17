package com.programmers.bucketback.domains.item.api.dto.response;

import java.util.List;

import com.programmers.bucketback.domains.item.application.dto.ItemGetNamesServiceResponse;
import com.programmers.bucketback.domains.item.application.dto.ItemNameGetResult;

public record ItemGetNamesResponse(
	List<ItemNameGetResult> itemNameGetResults
) {
	public static ItemGetNamesResponse from(final ItemGetNamesServiceResponse response) {
		List<ItemNameGetResult> itemNameGetResults = response.itemNameGetResults().stream()
			.map(ItemNameGetResult::from)
			.toList();

		return new ItemGetNamesResponse(itemNameGetResults);
	}
}
