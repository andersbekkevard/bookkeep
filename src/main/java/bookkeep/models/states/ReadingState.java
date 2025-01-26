package bookkeep.models.states;

import bookkeep.models.OwnedBook;

public abstract class ReadingState {

	protected OwnedBook book;

	public abstract void startReading();

	public abstract void stopReading();

	public abstract String getStateName();
}
