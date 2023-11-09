package com.programmers.bucketback.domains.member.application;

import java.util.List;

import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.inventory.domain.Inventory;

public record InventoryProfile(
	Long id,
	Hobby hobby,
	List<String> images
) {
	public static InventoryProfile of(
		final Inventory inventory,
		final List<String> itemImages
	) {
		return new InventoryProfile(inventory.getId(), inventory.getHobby(), itemImages);
	}
}
