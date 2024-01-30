package com.programmers.bucketback.global.config.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.programmers.bucketback.global.error.ChatErrorHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	private final ChatPreHandler chatPreHandler;

	/**
	 * STOMP 프로토콜을 사용하여 클라이언트와 서버가 메시지를 주고받을 수 있도록 엔드포인트를 등록합니다.
	 */
	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp") // ws-stomp 엔드포인트를 통해 클라이언트가 서버와 연결할 수 있습니다.
			.setAllowedOriginPatterns("*")
			.withSockJS(); // withSockJS() 메소드는 SockJS를 사용할 수 있도록 합니다.

		registry.setErrorHandler(new ChatErrorHandler());
	}

	/**
	 * 클라이언트가 메시지를 구독할 수 있도록 메시지 브로커를 등록합니다.
	 */
	@Override
	public void configureMessageBroker(final MessageBrokerRegistry registry) {
		// /subscribe로 시작하는 경로를 구독할 수 있도록 등록합니다.
		registry.enableSimpleBroker("/subscribe");

		// /app으로 시작하는 경로로 들어오는 메시지를 컨트롤러에서 처리할 수 있도록 등록합니다.
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void configureClientInboundChannel(final ChannelRegistration registration) {
		registration.interceptors(chatPreHandler);
	}
}
