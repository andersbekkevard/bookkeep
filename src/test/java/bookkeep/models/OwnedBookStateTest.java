package bookkeep.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookkeep.enums.BookFormat;
import bookkeep.enums.EventType;
import bookkeep.enums.Genre;

class OwnedBookStateTest {

	private OwnedBook ownedBook;

	@BeforeEach
	void setUp() {
		ownedBook = new OwnedBook("Author", 2023, 300, Genre.FICTION, BookFormat.PHYSICAL);
	}

	@Test
	void testInitialNotStartedState() {
		assertEquals("NotStartedState", ownedBook.getState().getStateName(), "Initial state should be NotStartedState");
	}

	@Test
	void testStartReadingTransitionsToInProgressState() {
		ownedBook.getState().startReading();
		assertEquals("InProgressState", ownedBook.getState().getStateName(),
				"State should transition to InProgressState");
	}

	@Test
	void testFinishReadingTransitionsToFinishedState() {
		ownedBook.getState().startReading();
		ownedBook.getState().stopReading();
		assertEquals("FinishedState", ownedBook.getState().getStateName(), "State should transition to FinishedState");
	}

	@Test
	void testCommentInDifferentStates() {
		// NotStartedState
		assertThrows(UnsupportedOperationException.class, () -> ownedBook.addComment("Comment in NotStarted"));
		assertEquals(0, ownedBook.getHistory().getListOfEvents().size(), "History should record the comment event");

		// Transition to InProgressState and comment
		ownedBook.getState().startReading();
		ownedBook.addComment("Comment in InProgress");
		assertEquals(1, ownedBook.getHistory().getListOfEvents().size(),
				"History should record the comment in InProgressState");

		// Transition to FinishedState and comment
		ownedBook.getState().stopReading();
		ownedBook.addComment("Comment in Finished");
		assertEquals(2, ownedBook.getHistory().getListOfEvents().size(),
				"History should record the comment in FinishedState");
	}

	@Test
	void testAddQuote() {
		ownedBook.getState().startReading();
		ownedBook.addQuote("This is a quote.", 1);
		assertEquals(1, ownedBook.getHistory().getListOfEvents().size(), "History should record the quote event");
		assertEquals(EventType.QUOTE, ownedBook.getHistory().getListOfEvents().get(0).getType(),
				"Event type should be QUOTE");
	}

	@Test
	void testAddReview() {
		ownedBook.getState().startReading();
		ownedBook.getState().stopReading();
		ownedBook.review("Great book!", 5);

		// Verify the review is stored as a separate field
		assertEquals("Great book!", ownedBook.getHistory().getReview().getText(), "Review comment should match");
		// TODO getrating not implemented assertEquals(5,
		// ownedBook.getHistory().getReview().getRating(), "Review rating should
		// match");

		// Ensure no additional events are added to the list for the review
		assertEquals(0, ownedBook.getHistory().getListOfEvents().stream()
				.filter(event -> event.getType() == EventType.REVIEW).count(),
				"Review should not be stored in the events list");
	}

	@Test
	void testIncrementPageNumber() {
		ownedBook.getState().startReading();
		ownedBook.incrementPageNumber(1);
		assertEquals(1, ownedBook.getPageNumber(), "Page number should increment by 1");

		ownedBook.incrementPageNumber(1);
		assertEquals(2, ownedBook.getPageNumber(), "Page number should increment by 1 again");
	}

	@Test
	void testReadingDuration() throws InterruptedException {
		ownedBook.getState().startReading();
		Thread.sleep(1000); // Simulate 1 second of reading
		ownedBook.getState().stopReading();

		assertTrue(ownedBook.getReadingDuration().compareTo(java.time.Duration.ZERO) > 0,
				"Reading duration should be greater than 0");
	}

	@Test
	void testIntegrationWithBookEventAndHistory() {
		// Start reading
		ownedBook.getState().startReading();
		assertEquals(EventType.STARTED_READING, ownedBook.getHistory().getStartedReading().getType(),
				"Event type should be STARTED_READING");

		// Finish reading
		ownedBook.getState().stopReading();
		assertEquals(EventType.FINISHED_READING, ownedBook.getHistory().getFinishedReading().getType(),
				"Event type should be FINISHED_READING");
	}
}
