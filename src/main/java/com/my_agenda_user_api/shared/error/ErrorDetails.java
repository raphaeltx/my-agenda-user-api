package com.my_agenda_user_api.shared.error;

import java.time.LocalDateTime;

public class ErrorDetails {

	private LocalDateTime timestamp;

	private String message;

	private String Details;

	public ErrorDetails() {
	}

	public ErrorDetails(LocalDateTime timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		Details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return Details;
	}

}
