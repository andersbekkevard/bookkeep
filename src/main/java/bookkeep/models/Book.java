package bookkeep.models;

import bookkeep.enums.EventType;
import bookkeep.enums.Genre;

public abstract class Book {
	private String authorName;
	private int publicationYear;
	private int pageCount;
	private Genre genre;
	private BookHistory history = new BookHistory();

	public Book() {
	}

	public Book(String authorName, int publicationYear, int pageCount, Genre genre) {
		this.authorName = authorName;
		this.publicationYear = publicationYear;
		this.pageCount = pageCount;
		this.genre = genre;
	}

	public void comment(String comment) {
		BookEvent event = new BookEvent(EventType.COMMENT, comment);
		history.addEvent(event);
	}

	// region Getters and Setters
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public int getPageCount() {
		return pageCount;
	}

	public BookHistory getHistory() {
		return history;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	// endregion

	@Override
	public String toString() {
		return "authorName=" + authorName + ", publicationYear=" + publicationYear + ", pageCount=" + pageCount
				+ ", genre=" + genre;
	}

}
