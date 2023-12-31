package com.programmers.bucketback.domains.member.implementation;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.bucketback.domains.member.domain.Member;
import com.programmers.bucketback.domains.member.domain.MemberBuilder;

@ExtendWith(MockitoExtension.class)
class MemberModifierTest {

	@InjectMocks
	private MemberModifier memberModifier;

	@Mock
	private MemberChecker memberChecker;

	@ParameterizedTest
	@CsvSource({"nickname, 0", "best_park, 1"})
	@DisplayName("주어진 닉네임과 자기소개로 프로필을 수정한다.")
	void modifyProfileTest(
		final String nickname,
		final int time
	) {
		// given
		final Member member = MemberBuilder.build(1L);
		final String introduction = "안녕하세요!";

		// when
		memberModifier.modifyProfile(member, nickname, introduction);

		// then
		assertThat(member.getNickname()).isEqualTo(nickname);
		assertThat(member.getIntroduction()).isEqualTo(introduction);
		then(memberChecker).should(times(time)).checkNicknameDuplication(nickname);
	}

	@Test
	@DisplayName("비밀번호를 변경한다.")
	void modifyPasswordTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final String encodedPassword = "$2b$12$9136HMSjeym7mmJ5OgvCPusoDmCmAN5w1caMkXN8s7OuklKr755y6";

		// when
		memberModifier.modifyPassword(member, encodedPassword);

		// then
		assertThat(member.getPassword()).isEqualTo(encodedPassword);
	}

	@Test
	@DisplayName("프로필 이미지를 변경한다.")
	void modifyProfileImageTest() {
		// given
		final Member member = MemberBuilder.build(1L);
		final String directory = "resized";
		final String profileImage = UUID.randomUUID() + ".png";
		final String IMAGE_BASE_PATH = "https://team-02-bucket.s3.ap-northeast-2.amazonaws.com/";
		final String imagePath = IMAGE_BASE_PATH + directory + "/" + profileImage;

		// when
		memberModifier.modifyProfileImage(member, directory, profileImage);

		// then
		assertThat(member.getProfileImage()).isEqualTo(imagePath);
	}
}
