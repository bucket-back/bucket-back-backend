package com.programmers.bucketback.domains.inventory.application;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReviewedItem {
	private Long itemId;
	private String name;
	private Integer price;
	private String image;
	private boolean isSelected;
	private LocalDateTime createdAt;
}