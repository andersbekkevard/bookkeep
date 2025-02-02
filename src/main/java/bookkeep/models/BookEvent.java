package bookkeep.models;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import bookkeep.enums.EventType;

/**
 * Represents an event related to a book's history, such as starting, stopping,
 * commenting, reviewing, or quoting. Each event type determines which fields
 * are relevant:
 * 
 * @param timestamp  The time the event occurred.
 *                   Used by all event types.
 * 
 * @param type       The type of the event (for example STARTED_READING,
 *                   COMMENT,
 *                   REVIEW).
 * 
 * @param text       The text associated with the event.
 *                   Used only for COMMENT, QUOTE, AFTERTHOUGHT, and REVIEW.
 * 
 * @param pageNumber The page number associated with the event.
 *                   Used only for COMMENT, QUOTE, and AFTERTHOUGHT.
 * 
 * @param rating     A rating from 0 to 5.
 *                   Used only for REVIEW.
 */

public class BookEvent implements Serializable {

	private Instant timestamp;
	private EventType type;
	private String text;
	private int pageNumber;
	private int rating;

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final int LOWEST_RATING = 0;
	private static final int HIGHEST_RATING = 5;

	/**
	 * Constructor 1 for Non-Comment-Types
	 */
	public BookEvent(EventType type) {
		if (!(type == EventType.STARTED_READING) && !(type == EventType.FINISHED_READING)) {
			throw new IllegalArgumentException(
					"Event without String or Int has to be Start or Stop");
		}
		this.timestamp = Instant.now();
		this.type = type;
	}

	/**
	 * Constructor 2 for Comments, Quotes or Rating
	 */
	public BookEvent(EventType type, String text, int number) {
		if ((type == EventType.STARTED_READING) || (type == EventType.FINISHED_READING)) {
			throw new IllegalArgumentException("Cant start or stop with input String and int");
		}

		this.timestamp = Instant.now();
		this.type = type;
		this.text = text;

		// The number can be intended as either a pageNumber or a rating
		if (type != EventType.REVIEW) {
			this.pageNumber = number;
			return;
		}

		// At this point we know the event is a review
		if (LOWEST_RATING > number || number > HIGHEST_RATING) {
			throw new IllegalArgumentException("Rating has to be between 0 and 5");
		}
		this.rating = number;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public EventType getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BookEvent{");
		sb.append("timestamp=").append(timestamp);
		sb.append(", type=").append(type);
		sb.append(", text=").append(text);
		sb.append(", pageNumber=").append(pageNumber);
		sb.append('}');
		return sb.toString();
	}

}
