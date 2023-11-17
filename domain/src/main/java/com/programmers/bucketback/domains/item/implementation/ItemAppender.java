package com.programmers.bucketback.domains.item.implementation;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.model.ItemCreateServiceRequest;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemAppender {

	private final ItemRepository itemRepository;

	public Long append(final ItemCreateServiceRequest request) {
		Item item = Item.builder().
			hobby(request.hobby()).
			image(request.imageUrl()).
			url(request.url()).
			price(request.price()).
			name(request.itemName())
			.build();

		Item savedItem = itemRepository.save(item);
		return savedItem.getId();
	}
}