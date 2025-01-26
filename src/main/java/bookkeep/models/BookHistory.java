package bookkeep.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookHistory {
	private final List<BookEvent> listOfEvents;

	private static final Comparator<BookEvent> chronologicalComparator = (e1, e2) -> e1.getTimestamp()
			.compareTo(e2.getTimestamp());

	public BookHistory() {
		this.listOfEvents = new ArrayList<>();
	}

	public List<BookEvent> getListOfEvents() {
		return listOfEvents;
	}

	public void addEvent(BookEvent event) {
		this.listOfEvents.add(event);
	}

	public void sort() {
		listOfEvents.sort(chronologicalComparator);
	}

	@Override
	public String toString() {
		return listOfEvents.toString();
	}
}
