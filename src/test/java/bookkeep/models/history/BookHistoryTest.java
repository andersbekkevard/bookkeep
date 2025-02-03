package bookkeep.models.history;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookkeep.enums.EventType;

class BookHistoryTest {

	private BookHistory history;

	@BeforeEach
	void setUp() {
		history = new BookHistory();
	}

	/*
	 * ================================= INITIAL STATE TESTS
	 * =================================
	 */

	@Test
	void testInitialHistoryIsEmpty() {
		List<BookEvent> events = history.getListOfEvents();
		assertNotNull(events, "Event list should not be null");
		assertTrue(events.isEmpty(), "Event list should be empty initially");
	}

	/* ============================= ADD EVENT TESTS ============================ */

	@Test
	void testAddCommentEvent() {
		BookEvent commentEvent = new BookEvent(EventType.COMMENT, "Great book", 10);
		history.addEvent(commentEvent);
		List<BookEvent> events = history.getListOfEvents();
		assertEquals(1, events.size(), "Should have one event after adding a comment");
		assertEquals(commentEvent, events.get(0), "The added comment event should match");
	}

	@Test
	void testAddQuoteEvent() {
		BookEvent quoteEvent = new BookEvent(EventType.QUOTE, "Inspiring quote", 20);
		history.addEvent(quoteEvent);
		List<BookEvent> events = history.getListOfEvents();
		assertEquals(1, events.size(), "Should have one event after adding a quote");
		assertEquals(quoteEvent, events.get(0), "The added quote event should match");
	}

	@Test
	void testAddAfterThoughtEvent() {
		BookEvent afterThoughtEvent = new BookEvent(EventType.AFTERTHOUGHT, "Late thought", 30);
		history.addEvent(afterThoughtEvent);
		List<BookEvent> events = history.getListOfEvents();
		assertEquals(1, events.size(), "Should have one event after adding an afterthought");
		assertEquals(afterThoughtEvent, events.get(0), "The added afterthought event should match");
	}

	@Test
	void testAddStartedReadingEvent() {
		BookEvent startedEvent = new BookEvent(EventType.STARTED_READING);
		history.addEvent(startedEvent);
		assertEquals(startedEvent, history.getStartedReading(), "Started reading event should be set");
	}

	@Test
	void testAddFinishedReadingEvent() {
		BookEvent finishedEvent = new BookEvent(EventType.FINISHED_READING);
		history.addEvent(finishedEvent);
		assertEquals(finishedEvent, history.getFinishedReading(), "Finished reading event should be set");
	}

	@Test
	void testAddReviewEventThrowsException() {
		BookEvent reviewEvent = new BookEvent(EventType.REVIEW, "Review text", 4);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> history.addEvent(reviewEvent));
		assertTrue(exception.getMessage().contains("Reviews should not be added through addEvent Method"),
				"Adding a review event through addEvent should throw an exception");
	}

	/* =========================== GATHER SUBSET TESTS ========================== */

	@Test
	void testGetCommentsSubset() {
		BookEvent comment1 = new BookEvent(EventType.COMMENT, "Comment one", 5);
		BookEvent comment2 = new BookEvent(EventType.COMMENT, "Comment two", 10);
		BookEvent quote = new BookEvent(EventType.QUOTE, "A quote", 15);
		history.addEvent(comment1);
		history.addEvent(quote);
		history.addEvent(comment2);

		List<BookEvent> comments = history.getComments();
		assertEquals(2, comments.size(), "There should be 2 comment events");
		assertTrue(comments.contains(comment1));
		assertTrue(comments.contains(comment2));
	}

	@Test
	void testGetAfterThoughtsSubset() {
		BookEvent afterThought = new BookEvent(EventType.AFTERTHOUGHT, "Afterthought", 20);
		BookEvent comment = new BookEvent(EventType.COMMENT, "Comment", 5);
		history.addEvent(afterThought);
		history.addEvent(comment);

		List<BookEvent> afterThoughts = history.getAfterThoughts();
		assertEquals(1, afterThoughts.size(), "There should be 1 afterthought event");
		assertEquals(afterThought, afterThoughts.get(0));
	}

	@Test
	void testGetQuotesSubset() {
		BookEvent quote1 = new BookEvent(EventType.QUOTE, "Quote one", 25);
		BookEvent quote2 = new BookEvent(EventType.QUOTE, "Quote two", 30);
		BookEvent comment = new BookEvent(EventType.COMMENT, "Comment", 5);
		history.addEvent(quote1);
		history.addEvent(comment);
		history.addEvent(quote2);

		List<BookEvent> quotes = history.getQuotes();
		assertEquals(2, quotes.size(), "There should be 2 quote events");
		assertTrue(quotes.contains(quote1));
		assertTrue(quotes.contains(quote2));
	}

	/* ================================ SORT TEST =============================== */

	@Test
	void testSortOrdersEventsChronologically() throws InterruptedException {
		// Create events with a slight delay between them.
		BookEvent event1 = new BookEvent(EventType.COMMENT, "First", 5);
		Thread.sleep(10);
		BookEvent event2 = new BookEvent(EventType.QUOTE, "Second", 10);
		Thread.sleep(10);
		BookEvent event3 = new BookEvent(EventType.AFTERTHOUGHT, "Third", 15);

		// Add them in reverse order.
		history.addEvent(event3);
		history.addEvent(event1);
		history.addEvent(event2);

		// Call sort() to arrange them chronologically.
		history.sort();

		List<BookEvent> sortedEvents = history.getListOfEvents();
		assertEquals(event1, sortedEvents.get(0), "Event1 should be first after sorting");
		assertEquals(event2, sortedEvents.get(1), "Event2 should be second after sorting");
		assertEquals(event3, sortedEvents.get(2), "Event3 should be third after sorting");
	}

	/* ========================== REVIEW GET/SET TEST ========================== */

	@Test
	void testReviewGetterAndSetter() {
		BookEvent reviewEvent = new BookEvent(EventType.REVIEW, "Excellent!", 5);
		history.setReview(reviewEvent);
		assertTrue(history.hasReview(), "hasReview() should return true after setting a review");
		assertEquals(reviewEvent, history.getReview(), "getReview() should return the review event that was set");
	}

	/* ============================== TOSTRING TEST ============================= */
	@Test
	void testToStringReturnsListContents() {
		BookEvent commentEvent = new BookEvent(EventType.COMMENT, "Test comment", 10);
		history.addEvent(commentEvent);
		String toStringOutput = history.toString();
		assertTrue(toStringOutput.contains("Test comment"), "toString() should contain the comment text");
	}
}
