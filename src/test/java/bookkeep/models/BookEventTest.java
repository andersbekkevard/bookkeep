package bookkeep.models;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bookkeep.enums.EventType;

public class BookEventTest {

	@Test
	public void testConstructorForStartedReading() {
		// Test creation of STARTED_READING event
		BookEvent event = new BookEvent(EventType.STARTED_READING);

		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.STARTED_READING, event.getType(), "Event type should be STARTED_READING");
		assertNull(event.getComment(), "Comment should be null for non-COMMENT events");
	}

	@Test
	public void testConstructorForFinishedReading() {
		// Test creation of FINISHED_READING event
		BookEvent event = new BookEvent(EventType.FINISHED_READING);

		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.FINISHED_READING, event.getType(), "Event type should be FINISHED_READING");
		assertNull(event.getComment(), "Comment should be null for non-COMMENT events");
	}

	@Test
	public void testConstructorForCommentEvent() {
		// Test creation of COMMENT event with a valid comment
		String comment = "This is a test comment.";
		BookEvent event = new BookEvent(EventType.COMMENT, comment);

		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.COMMENT, event.getType(), "Event type should be COMMENT");
		assertEquals(comment, event.getComment(), "Comment should match the provided string");
	}

	@Test
	public void testInvalidCommentWithoutString() {
		// Test invalid case: COMMENT event without a comment string
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.COMMENT);
		});

		assertEquals("Cannot create EventType comment without a String comment", exception.getMessage(),
				"Should throw exception for COMMENT event without a comment");
	}

	@Test
	public void testInvalidNonCommentWithString() {
		// Test invalid case: STARTED_READING or FINISHED_READING with a comment
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.STARTED_READING, "Invalid comment");
		});

		assertEquals("Comment not supported for Start or Stop event", exception.getMessage(),
				"Should throw exception for STARTED_READING or FINISHED_READING event with a comment");
	}

	@Test
	public void testToStringForStartedReading() {
		// Test the toString method for a STARTED_READING event
		BookEvent event = new BookEvent(EventType.STARTED_READING);
		String result = event.toString();

		assertTrue(result.contains("type=STARTED_READING"), "toString should include 'type=STARTED_READING'");
		assertTrue(result.contains("timestamp="), "toString should include 'timestamp='");
	}

	@Test
	public void testToStringForCommentEvent() {
		// Test the toString method for a COMMENT event
		String comment = "This is a sample comment.";
		BookEvent event = new BookEvent(EventType.COMMENT, comment);
		String result = event.toString();

		assertTrue(result.contains("type=COMMENT"), "toString should include 'type=COMMENT'");
		assertTrue(result.contains("timestamp="), "toString should include 'timestamp='");
		assertTrue(result.contains("comment=" + comment), "toString should include the comment");
	}

	@Test
	public void testTimestampConversion() {
		// Test that getTimestamp correctly converts Instant to LocalDateTime
		BookEvent event = new BookEvent(EventType.STARTED_READING);
		LocalDateTime localDateTime = event.getTimestamp();

		assertNotNull(localDateTime, "Converted LocalDateTime should not be null");
	}
}
