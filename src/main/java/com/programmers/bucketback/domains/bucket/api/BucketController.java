package com.programmers.bucketback.domains.bucket.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.bucketback.domains.bucket.api.dto.request.BucketCreateRequest;
import com.programmers.bucketback.domains.bucket.api.dto.request.BucketUpdateRequest;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetByCursorResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetMemberItemResponse;
import com.programmers.bucketback.domains.bucket.api.dto.response.BucketGetResponse;
import com.programmers.bucketback.domains.bucket.application.BucketService;
import com.programmers.bucketback.domains.common.Hobby;
import com.programmers.bucketback.domains.common.vo.CursorRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "buckets", description = "버킷 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BucketController {

	private final BucketService bucketService;

	@Operation(summary = "버킷 생성", description = "BucketCreateRequest 을 이용하여 버킷을 생성힙니다.")
	@PostMapping("/buckets")
	public ResponseEntity<Void> createBucket(@RequestBody @Valid final BucketCreateRequest request) {
		bucketService.createBucket(request.toInfo(), request.toRegistry());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "버킷 수정", description = "BucketId, BucketUpdateRequest 을 이용하여 버킷을 수정힙니다.")
	@PutMapping("/buckets/{bucketId}")
	public ResponseEntity<Void> modifyBucket(
		@PathVariable final Long bucketId,
		@RequestBody @Valid final BucketUpdateRequest request
	) {
		bucketService.modifyBucket(bucketId, request.toInfo(), request.toRegistry());

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "버킷 수정을 위한 내가 담은 아이템 목록 커서 조회")
	@GetMapping("/buckets/{bucketId}/myitems")
	public ResponseEntity<BucketGetMemberItemResponse> getMemberItemsForModify(
		@PathVariable final Long bucketId,
		@ModelAttribute @Valid final CursorRequest cursorRequest
	) {
		BucketGetMemberItemResponse bucketGetMemberItemResponse =
			bucketService.getMemberItemsForModify(bucketId, cursorRequest.toParameters());

		return ResponseEntity.ok(bucketGetMemberItemResponse);
	}

	@Operation(summary = "버킷 삭제", description = "BucketId을 이용하여 버킷을 삭제힙니다.")
	@DeleteMapping("/buckets/{bucketId}")
	public ResponseEntity<Void> deleteBucket(@PathVariable final Long bucketId) {
		bucketService.deleteBucket(bucketId);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "버킷 상세 조회", description = "BucketId을 이용하여 버킷을 조회힙니다.")
	@GetMapping("/{nickname}/buckets/{bucketId}")
	public ResponseEntity<BucketGetResponse> getBucket(
		@PathVariable final String nickname,
		@PathVariable final Long bucketId
	) {
		return ResponseEntity.ok(bucketService.getBucket(bucketId));
	}

	@Operation(summary = "버킷 목록 조회(커서)", description = "유저이름, 취미, 커서 방식 조회 요청을 이용하여 버킷을 조회힙니다.")
	@GetMapping("/{nickname}/buckets")
	public ResponseEntity<BucketGetByCursorResponse> getBucketsByCursor(
		@PathVariable final String nickname,
		@RequestParam final String hobby,
		@ModelAttribute @Valid final CursorRequest request
	) {
		BucketGetByCursorResponse response = bucketService.getBucketsByCursor(
			nickname,
			Hobby.valueOf(hobby.toUpperCase()),
			request.toParameters()
		);

		return ResponseEntity.ok(response);
	}
}
