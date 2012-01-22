package music.chord;

import java.util.ArrayList;
import java.util.List;

public class Voicing extends AbstractVoicing {
	public static Voicing getInstance() {
		return new Voicing();
	}
	
	private List<ChordMember> chordMemberList;
	
	private Voicing() {
		chordMemberList = new ArrayList<ChordMember>();
	}
	
	public void addChordMember(ChordMember member) {
		chordMemberList.add(member);
	}
	
	public String toString() {
		return chordMemberList.toString();
	}

	public List<Integer> voice(Chord chord) {
		List<Integer> result = new ArrayList<Integer>();
		int bass = chord.chromaticIndexFromChordMember(chordMemberList.get(0));
		int baritone = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(1)), bass);
		int lead = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(2)), baritone);
		int tenor = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(3)), lead);
		
		result.add(bass);
		result.add(baritone);
		result.add(lead);
		result.add(tenor);
		
		return result;
	}
}
