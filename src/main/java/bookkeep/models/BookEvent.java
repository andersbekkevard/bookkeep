package bookkeep.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import bookkeep.enums.EventType;

public class BookEvent {
	// We are storing timestamps as UTC Instant, but all methods will be using
	// LocalDateTime (To simplify formatting etc)
	private Instant timestamp;

	// Each Event has a type
	private EventType type;

	// Only Events of type == EventType.COMMENT have a String comment attached
	private String comment;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// Constructor 1 for Non-Comment-Types
	public BookEvent(EventType type) {
		if (type == EventType.COMMENT) {
			throw new IllegalArgumentException("Cannot create EventType comment without a String comment");
		}
		this.timestamp = Instant.now();
		this.type = type;
	}

	// Constructor 2 for Comment
	public BookEvent(EventType type, String comment) {
		if (type != EventType.COMMENT) {
			throw new IllegalArgumentException("Comment not supported for Start or Stop event");
		}
		this.timestamp = Instant.now();
		this.type = type;
		this.comment = comment;
	}

	public LocalDateTime getTimestamp() {
		return LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
	}

	public EventType getType() {
		return type;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		if (type != EventType.COMMENT) {
			return "type=" + type + ", timestamp=" + getTimestamp().format(formatter);
		}
		return "type=" + type + ", timestamp=" + getTimestamp().format(formatter) + ", comment=" + comment;
	}

}
