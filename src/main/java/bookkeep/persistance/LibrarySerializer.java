package bookkeep.persistance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bookkeep.models.collections.BookStorage;

public class LibrarySerializer {
	private static final String FILEPATH = "serializedlibrary\\library.ser";

	public void save(BookStorage library) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILEPATH))) {
			out.writeObject(library);
		} catch (Exception e) {
			throw new IOException(FILEPATH + " was not found");
		}
	}

	public BookStorage load() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILEPATH))) {
			return (BookStorage) in.readObject();
		} catch (Exception e) {
			// Dont bother throwing exceptions for missing file paths etc
			return new BookStorage();
		}
	}

	public static void main(String[] args) {
		LibrarySerializer serializer = new LibrarySerializer();
		BookStorage library = serializer.load();
		System.out.println(library);
	}

}
