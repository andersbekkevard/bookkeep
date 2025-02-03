package bookkeep.models.states;

import java.time.Duration;
import java.time.Instant;

import bookkeep.enums.EventType;
import bookkeep.models.OwnedBook;
import bookkeep.models.history.BookEvent;

public class InProgressState extends ReadingState {

	public InProgressState(OwnedBook book) {
		super(book);
	}

	@Override
	public void startReading() {
		throw new UnsupportedOperationException("Cannot start a book in InProgressState");
	}

	@Override
	public void stopReading() {
		book.setState(new FinishedState(book));
		BookEvent finishedReadingEvent = new BookEvent(EventType.FINISHED_READING);
		book.getHistory().addEvent(finishedReadingEvent);
	}

	@Override
	public String getStateName() {
		return "InProgressState";
	}

	@Override
	public void handleComment(String comment) {
		BookEvent commentEvent = new BookEvent(EventType.COMMENT, comment, book.getPageNumber());

		book.getHistory().addEvent(commentEvent);
	}

	@Override
	public void handleQuote(String quote, int quotePageNumber) {
		BookEvent quoteEvent = new BookEvent(EventType.QUOTE, quote, quotePageNumber);
		book.getHistory().addEvent(quoteEvent);
	}

	@Override
	public void handleReview(String reviewText, int rating) {
		throw new UnsupportedOperationException("Cannot review an unfinished book");
	}

	@Override
	public Duration handleReadingDuration() {
		// Here we can assume that the BookHistory should have a startedReadingEvent
		Instant timeOfStartedReading = book.getHistory().getStartedReading().getTimestamp();

		return Duration.between(timeOfStartedReading, Instant.now());
	}

	@Override
	public void handleIncrementPageNumber(int increment) {
		// Validation Logic for pageNumber is done in setPageNumber method
		int newPageNumber = book.getPageNumber() + increment;
		book.setPageNumber(newPageNumber);
	}

}
