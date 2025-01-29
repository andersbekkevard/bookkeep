package bookkeep.models.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import bookkeep.models.Book;

public class BookStorage {

	private final Map<UUID, Book> bookRepository;
	private final List<BookCollection> bookShelves;

	// region BiPredicate Sorters
	private static final BiPredicate<Book, Object> filterByAuthor = (book, author) -> book.getAuthorName()
			.equals(author);

	private static final BiPredicate<Book, Object> filterByTitle = (book, title) -> book.getTitle().equals(title);

	private static final BiPredicate<Book, Object> filterByPublicationYear = (book, year) -> Integer
			.valueOf(book.getPublicationYear()).equals(year);
	// endregion

	// region Base Methods
	public BookStorage() {
		this.bookRepository = new HashMap<>();
		this.bookShelves = new ArrayList<>();
	}

	public void addBook(Book book) {
		bookRepository.put(book.getUUID(), book);
	}

	public void deleteBook(UUID id) {
		bookRepository.remove(id);
		for (BookCollection shelf : bookShelves) {
			shelf.removeId(id);
		}
	}

	public Book getBook(UUID id) {
		return bookRepository.get(id);
	}

	public void addCollection(String name) {
		BookCollection shelf = new BookCollection(name);
		bookShelves.add(shelf);
	}

	public void removeCollection(String name) {
		for (BookCollection shelf : bookShelves) {
			if (shelf.getName().equals(name)) {
				bookShelves.remove(shelf);
			}
		}
	}
	// endregion

	// region filtering logic
	public Optional<BookCollection> getShelfByName(String name) {
		return bookShelves.stream()
				.filter(shelf -> shelf.getName().equals(name))
				.findFirst();
	}

	public List<Book> getBooksFromShelf(BookCollection shelf) {
		return shelf.getUUIDs().stream().map(bookRepository::get).collect(Collectors.toList());
	}

	public List<UUID> findUUIDs(BiPredicate<Book, Object> condition, Object value) {
		return bookRepository.values().stream()
				.filter(book -> condition.test(book, value))
				.map(Book::getUUID)
				.collect(Collectors.toList());
	}

	public List<UUID> findUUIDByAuthor(String author) {
		return findUUIDs(filterByAuthor, author);
	}

	public List<UUID> findUUIDByTitle(String title) {
		return findUUIDs(filterByTitle, title);
	}

	public List<UUID> findUUIDByYear(int year) {
		return findUUIDs(filterByPublicationYear, year);
	}

	// endregion

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Books: [");

		for (Book book : bookRepository.values()) {
			result.append(book.toString()).append(", ");
		}

		// Safely remove the last comma and space, only if books exist
		if (!bookRepository.isEmpty()) {
			result.setLength(result.length() - 2);
		}
		result.append("]");

		return result.toString();
	}
}
