package com.programmers.bucketback.domains.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Hobby {

	BASEBALL("농구");

	@JsonValue
	final private String hobbyValue;

	Hobby(final String hobbyValue) {
		this.hobbyValue = hobbyValue;
	}
}
