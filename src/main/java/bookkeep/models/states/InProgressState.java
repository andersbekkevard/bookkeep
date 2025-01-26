package bookkeep.models.states;

public class InProgressState extends ReadingState {

	@Override
	public void startReading() {
		throw new UnsupportedOperationException("Cannot start a book in InProgressState");
	}

	@Override
	public void stopReading() {
		book.setState(new FinishedState());
	}

	@Override
	public String getStateName() {
		return "InProgressState";
	}

}
