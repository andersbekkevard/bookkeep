package bookkeep.models.states;

public class FinishedState extends ReadingState {

	@Override
	public void startReading() {
		throw new UnsupportedOperationException("ReReading not supported yet");
	}

	@Override
	public void stopReading() {
		throw new UnsupportedOperationException("Cannot stop a book in FinishedState");
	}

	@Override
	public String getStateName() {
		return "FinishedState";
	}

}
