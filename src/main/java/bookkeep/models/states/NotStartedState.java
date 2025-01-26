package bookkeep.models.states;

public class NotStartedState extends ReadingState {

	@Override
	public void startReading() {
		book.setState(new InProgressState());
	}

	@Override
	public void stopReading() {
		throw new UnsupportedOperationException("Cannot stop a book in NotStartedState");
	}

	@Override
	public String getStateName() {
		return "NotStartedState";
	}

}
