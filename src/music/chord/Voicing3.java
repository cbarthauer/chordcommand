package music.chord;

import java.util.ArrayList;
import java.util.List;

public class Voicing3 extends AbstractVoicingStrategy {

	@Override
	public List<Integer> voice(Chord chord) {
		List<Integer> result = new ArrayList<Integer>();
		int bass = chord.getRootNoteName().getChromaticIndex();
		int baritone = placeAbove(chord.getThirdNoteName().getChromaticIndex(), bass);
		int lead = placeAbove(chord.getFifthNoteName().getChromaticIndex(), baritone);
		int tenor = placeAbove(chord.getRootNoteName().getChromaticIndex(), lead);
		
		result.add(bass);
		result.add(baritone);
		result.add(lead);
		result.add(tenor);
		
		return result;
	}

}
