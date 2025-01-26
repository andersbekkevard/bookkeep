package bookkeep.app;

import bookkeep.models.OwnedBook;

public class Main {

	public static void main(String[] args) {
		System.out.print("\033[2J\033[1;1H");

		OwnedBook book = new OwnedBook();
		System.out.println(book.getHistory());
		book.comment("THis is a testcomment");
		System.out.println(book.getHistory());
	}
}
