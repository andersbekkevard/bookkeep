package bookkeep.models;

import bookkeep.enums.BookFormat;
import bookkeep.enums.Genre;
import bookkeep.models.states.NotStartedState;
import bookkeep.models.states.ReadingState;

public class OwnedBook extends Book {
	// Only Owned books can have a format, thats why this field is declared here
	private BookFormat format;
	private ReadingState state = new NotStartedState();

	public OwnedBook() {
	}

	public OwnedBook(String authorName, int publicationYear, int pageCount, Genre genre, BookFormat format) {
		super(authorName, publicationYear, pageCount, genre);
		this.format = format;
	}

	public BookFormat getFormat() {
		return format;
	}

	public void setFormat(BookFormat format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return super.toString() + ", format=" + format;
	}

	public void setState(ReadingState state) {
		this.state = state;
	}

}
