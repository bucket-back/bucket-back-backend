package com.programmers.bucketback.global.config.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.programmers.bucketback.error.ErrorCode;
import com.programmers.bucketback.global.config.security.jwt.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ChatPreHandler implements ChannelInterceptor {

	private static final String JWT_PREFIX = "Bearer ";
	private static final String AUTH_HEADER_KEY = "Authorization";
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	public Message<?> preSend(
		final Message<?> message,
		final org.springframework.messaging.MessageChannel channel
	) {
		final String authHeader;
		final String jwt;
		final String memberId;
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (accessor == null) {
			throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
		}

		if (accessor.getCommand() == StompCommand.CONNECT || accessor.getCommand() == StompCommand.SEND) {
			authHeader = accessor.getFirstNativeHeader(AUTH_HEADER_KEY);

			if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
				throw new MalformedJwtException(ErrorCode.MEMBER_NOT_LOGIN.getMessage());
			}

			jwt = authHeader.substring(JWT_PREFIX.length());

			try {
				memberId = jwtService.extractUsername(jwt);

				if (memberId == null) {
					throw new MalformedJwtException(ErrorCode.BAD_SIGNATURE_JWT.getMessage());
				}

				final UserDetails userDetails = this.userDetailsService.loadUserByUsername(memberId);

				if (jwtService.isTokenValid(jwt, userDetails)) {
					final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
					);
					accessor.setUser(authToken);
				}

				return message;

			} catch (SignatureException | ExpiredJwtException e) {
				throw new MalformedJwtException(ErrorCode.BAD_SIGNATURE_JWT.getMessage());
			}
		}
		return message;
	}
}
