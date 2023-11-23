package com.programmers.bucketback.item.builder.model;

import com.programmers.bucketback.domains.item.model.ItemCrawlerInfo;

public class ItemCrawlerInfoBuilder {

	public static ItemCrawlerInfo successBuild() {
		return ItemCrawlerInfo.builder()
			.itemName("아이템")
			.price(10000)
			.url("https://www.naver.com")
			.imageUrl("https://www.naver.com//image")
			.build();
	}
}
