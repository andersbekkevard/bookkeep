package bookkeep.models;

import java.io.Serializable;
import java.util.UUID;

import bookkeep.enums.Genre;

public abstract class Book implements Serializable {
	protected String title;
	protected String authorName;
	protected int publicationYear;
	protected int pageCount;
	protected Genre genre;
	protected UUID id;

	/**
	 * Default constructor for debugging/flexibility
	 */
	public Book() {
	}

	public Book(String title, String authorName, int publicationYear, int pageCount, Genre genre) {
		this.title = title;
		this.authorName = authorName;
		this.publicationYear = publicationYear;
		this.pageCount = pageCount;
		this.genre = genre;
		this.id = UUID.randomUUID();
	}

	/**
	 * Getters and setters, Self Evident
	 */

	// region Getters and Setters
	public String getTitle() {
		return title;
	}

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

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public UUID getUUID() {
		return id;
	}

	// endregion

	@Override
	public String toString() {
		return "authorName=" + authorName + ", publicationYear=" + publicationYear + ", pageCount=" + pageCount
				+ ", genre=" + genre;
	}

}
