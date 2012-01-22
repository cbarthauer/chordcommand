package music.chord;


public abstract class AbstractVoicing implements VoicingStrategy {
	protected int placeAbove(int upper, int lower) {
		int result = upper;
		
		while(result <= lower) {
			result = result + 12;
		}
		
		return result;
	}
}
