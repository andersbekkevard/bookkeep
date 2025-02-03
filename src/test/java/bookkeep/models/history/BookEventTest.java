package bookkeep.models.history;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import bookkeep.enums.EventType;

class BookEventTest {

	@Test
	void testConstructor1ForStartedReading() {
		// Constructor 1 is allowed only for STARTED_READING and FINISHED_READING.
		BookEvent event = new BookEvent(EventType.STARTED_READING);
		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.STARTED_READING, event.getType(), "Type should be STARTED_READING");
	}

	@Test
	void testConstructor1ForFinishedReading() {
		BookEvent event = new BookEvent(EventType.FINISHED_READING);
		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.FINISHED_READING, event.getType(), "Type should be FINISHED_READING");
	}

	@Test
	void testConstructor1ThrowsForInvalidType() {
		// Using a type like COMMENT should fail in Constructor 1.
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.COMMENT);
		});
		assertTrue(exception.getMessage().contains("Event without String or Int has to be Start or Stop"));
	}

	@Test
	void testConstructor2ForComment() {
		// Constructor 2 is allowed for types such as COMMENT, QUOTE, AFTERTHOUGHT.
		BookEvent event = new BookEvent(EventType.COMMENT, "This is a comment", 45);
		assertNotNull(event.getTimestamp(), "Timestamp should not be null");
		assertEquals(EventType.COMMENT, event.getType(), "Type should be COMMENT");
		assertEquals("This is a comment", event.getText(), "Text should be set correctly");
		// For non-review events, the number is treated as a page number.
	}

	@Test
	void testConstructor2ForQuote() {
		BookEvent event = new BookEvent(EventType.QUOTE, "An inspiring quote", 50);
		assertEquals(EventType.QUOTE, event.getType(), "Type should be QUOTE");
		assertEquals("An inspiring quote", event.getText(), "Text should be set correctly");
	}

	@Test
	void testConstructor2ForReviewWithValidRating() {
		// For REVIEW events, the number is treated as a rating (must be between 0 and
		// 5).
		BookEvent event = new BookEvent(EventType.REVIEW, "A review", 4);
		assertEquals(EventType.REVIEW, event.getType(), "Type should be REVIEW");
		assertEquals("A review", event.getText(), "Review text should match");
		// Note: The rating is stored internally; if needed, you could test it via
		// reflection or toString.
	}

	@Test
	void testConstructor2ForReviewWithInvalidRatingLow() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.REVIEW, "Bad review", -1);
		});
		assertTrue(exception.getMessage().contains("Rating has to be between 0 and 5"));
	}

	@Test
	void testConstructor2ForReviewWithInvalidRatingHigh() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.REVIEW, "Bad review", 6);
		});
		assertTrue(exception.getMessage().contains("Rating has to be between 0 and 5"));
	}

	@Test
	void testConstructor2ThrowsForStartOrStopWithText() {
		Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.STARTED_READING, "Should fail", 0);
		});
		assertTrue(exception1.getMessage().contains("Cant start or stop with input String and int"));

		Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
			new BookEvent(EventType.FINISHED_READING, "Should fail", 0);
		});
		assertTrue(exception2.getMessage().contains("Cant start or stop with input String and int"));
	}

	@Test
	void testTimestampIsRecent() {
		Instant before = Instant.now().minus(1, ChronoUnit.SECONDS);
		BookEvent event = new BookEvent(EventType.STARTED_READING);
		Instant after = Instant.now().plus(1, ChronoUnit.SECONDS);
		assertTrue(event.getTimestamp().isAfter(before), "Timestamp should be after 'before'");
		assertTrue(event.getTimestamp().isBefore(after), "Timestamp should be before 'after'");
	}

	@Test
	void testToStringContainsRelevantInformation() {
		BookEvent event = new BookEvent(EventType.COMMENT, "Test comment", 12);
		String output = event.toString();
		assertTrue(output.contains("COMMENT"), "toString should contain event type");
		assertTrue(output.contains("Test comment"), "toString should contain text");
		assertTrue(output.contains("12"), "toString should contain the page number");
	}
}
