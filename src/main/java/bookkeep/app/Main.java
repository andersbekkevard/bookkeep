package bookkeep.app;

import bookkeep.models.collections.BookStorage;
import bookkeep.ui.LibraryMenu;

public class Main {

	public static void main(String[] args) {
		LibraryMenu menu = new LibraryMenu(new BookStorage());
		menu.start();
	}
}
