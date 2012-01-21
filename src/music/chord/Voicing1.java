package music.chord;

import java.util.ArrayList;
import java.util.List;

public class Voicing1 extends AbstractVoicingStrategy {
	@Override
	public List<Integer> voice(Chord chord) {
		List<Integer> result = new ArrayList<Integer>();
		int bass = chord.getRootNoteName().getChromaticIndex();
		int baritone = placeAbove(chord.getFifthNoteName().getChromaticIndex(), bass);
		int lead = placeAbove(chord.getRootNoteName().getChromaticIndex(), bass);
		int tenor = placeAbove(chord.getThirdNoteName().getChromaticIndex(), lead);
		
		result.add(bass);
		result.add(baritone);
		result.add(lead);
		result.add(tenor);
		
		return result;
	}
}
