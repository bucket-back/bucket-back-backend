package com.programmers.bucketback.domains.vote.application;

import org.springframework.stereotype.Component;

import com.programmers.bucketback.domains.item.domain.Item;
import com.programmers.bucketback.domains.item.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemReader {

	private final ItemRepository itemRepository;

	public Item read(final Long itemId) {
		return itemRepository.findById(itemId)
			.orElseThrow(() -> new EntityNotFoundException("아이템을 찾을 수 없습니다.")); // 추후 ITEM_NOT_FOUND 로 변경
	}
}
