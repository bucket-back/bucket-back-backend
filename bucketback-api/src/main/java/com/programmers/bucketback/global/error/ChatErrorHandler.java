package com.programmers.bucketback.global.error;

import java.nio.charset.StandardCharsets;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import com.programmers.bucketback.error.ErrorCode;

@Component
public class ChatErrorHandler extends StompSubProtocolErrorHandler {

	private static final String ERROR_CODE_PREFIX = "errorCode: ";

	public ChatErrorHandler() {
		super();
	}

	@Override
	public Message<byte[]> handleClientMessageProcessingError(final Message<byte[]> clientMessage, final Throwable ex) {
		if (ex.getCause().getMessage().equals(ErrorCode.BAD_SIGNATURE_JWT.getMessage())) {
			return prepareErrorMessage(ErrorCode.BAD_SIGNATURE_JWT);
		}

		return super.handleClientMessageProcessingError(clientMessage, ex);
	}

	private Message<byte[]> prepareErrorMessage(final ErrorCode errorCode) {

		String retErrorCodeMessage = ERROR_CODE_PREFIX + errorCode.getCode();
		StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);

		return MessageBuilder.createMessage(
			retErrorCodeMessage.getBytes(StandardCharsets.UTF_8),
			accessor.getMessageHeaders()
		);
	}

}