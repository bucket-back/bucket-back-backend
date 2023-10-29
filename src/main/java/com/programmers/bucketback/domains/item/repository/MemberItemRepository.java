package com.programmers.bucketback.domains.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.bucketback.domains.item.domain.MemberItem;

public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
}