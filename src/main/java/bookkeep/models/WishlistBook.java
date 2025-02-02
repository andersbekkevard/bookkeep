package bookkeep.models;

import bookkeep.enums.Genre;

public class WishlistBook extends Book {
	private int price;

	public WishlistBook() {
	}

	public WishlistBook(String title, String authorName, int publicationYear, int pageCount, Genre genre, int price) {
		super(title, authorName, publicationYear, pageCount, genre);
		this.price = price;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return super.toString() + ", price=" + price;
	}

}
