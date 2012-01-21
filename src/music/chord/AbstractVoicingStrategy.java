package music.chord;


public abstract class AbstractVoicingStrategy implements VoicingStrategy {

	protected int placeAbove(int upper, int lower) {
		int result = upper;
		
		while(result <= lower) {
			result = result + 12;
		}
		
		return result;
	}
}
