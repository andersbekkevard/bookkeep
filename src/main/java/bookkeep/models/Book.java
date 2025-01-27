package bookkeep.models;

/**
 * Abstract class that is the foundation of all books
 * Contains information every book must have
 * Is concretely implemented in {@link OwnedBook} and {@link WishlistBook} 
 */

import bookkeep.enums.Genre;

public abstract class Book {
	protected String authorName;
	protected int publicationYear;
	protected int pageCount;
	protected Genre genre;

	/**
	 * Default constructor for debugging/flexibility
	 */
	public Book() {
	}

	public Book(String authorName, int publicationYear, int pageCount, Genre genre) {
		this.authorName = authorName;
		this.publicationYear = publicationYear;
		this.pageCount = pageCount;
		this.genre = genre;
	}

	/**
	 * Getters and setters, Self Evident
	 */

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
