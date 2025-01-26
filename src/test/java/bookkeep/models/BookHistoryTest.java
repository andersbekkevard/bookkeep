
package bookkeep.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import bookkeep.enums.EventType;

public class BookHistoryTest {

	@Test
	public void testSortMethod() {
		// Create a BookHistory instance
		BookHistory bookHistory = new BookHistory();

		// Add unsorted BookEvents to the list
		BookEvent event1 = new BookEvent(EventType.STARTED_READING);
		BookEvent event2 = new BookEvent(EventType.COMMENT, "Great start!");
		BookEvent event3 = new BookEvent(EventType.FINISHED_READING);

		// Set custom timestamps for controlled testing
		LocalDateTime timestamp1 = LocalDateTime.of(2025, 1, 26, 10, 0);
		LocalDateTime timestamp2 = LocalDateTime.of(2025, 1, 26, 15, 0);
		LocalDateTime timestamp3 = LocalDateTime.of(2025, 1, 26, 20, 0);

		// Set timestamps (mocking real-world behavior)
		setEventTimestamp(event1, timestamp3);
		setEventTimestamp(event2, timestamp1);
		setEventTimestamp(event3, timestamp2);

		// Add events to the BookHistory
		bookHistory.addEvent(event1);
		bookHistory.addEvent(event2);
		bookHistory.addEvent(event3);

		// Sort the events
		bookHistory.sort();

		// Verify that the list is sorted chronologically
		List<BookEvent> sortedEvents = bookHistory.getListOfEvents();
		assertEquals(timestamp1, sortedEvents.get(0).getTimestamp(), "First event should have the earliest timestamp");
		assertEquals(timestamp2, sortedEvents.get(1).getTimestamp(), "Second event should have the middle timestamp");
		assertEquals(timestamp3, sortedEvents.get(2).getTimestamp(), "Third event should have the latest timestamp");
	}

	// Helper method to mock timestamps for BookEvent (reflection for testing
	// purposes)
	private void setEventTimestamp(BookEvent event, LocalDateTime timestamp) {
		try {
			java.lang.reflect.Field timestampField = BookEvent.class.getDeclaredField("timestamp");
			timestampField.setAccessible(true); // Bypass private modifier
			timestampField.set(event, timestamp.atZone(ZoneId.systemDefault()).toInstant());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			fail("Failed to set the timestamp for the event");
		}
	}
}
