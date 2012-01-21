package music.chord;

import java.util.ArrayList;
import java.util.List;

public class Voicing4 extends AbstractVoicingStrategy {

	@Override
	public List<Integer> voice(Chord chord) {
		List<Integer> result = new ArrayList<Integer>();
		int bass = chord.getFifthNoteName().getChromaticIndex();
		int baritone = placeAbove(chord.getRootNoteName().getChromaticIndex(), bass);
		int lead = placeAbove(chord.getThirdNoteName().getChromaticIndex(), baritone);
		int tenor = placeAbove(chord.getFifthNoteName().getChromaticIndex(), lead);
		
		result.add(bass);
		result.add(baritone);
		result.add(lead);
		result.add(tenor);
		
		return result;
	}

}
