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

	public List<NoteBean> voice(Chord chord) {
		List<NoteBean> result = new ArrayList<NoteBean>();
		
		int bass = chord.chromaticIndexFromChordMember(chordMemberList.get(0));
		NoteBean note = new NoteBean(chord.noteNameFromChordMember(chordMemberList.get(0)), bass);
		result.add(note);
		
		int baritone = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(1)), bass);
		note = new NoteBean(chord.noteNameFromChordMember(chordMemberList.get(1)), baritone);
		result.add(note);
		
		int lead = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(2)), baritone);
		note = new NoteBean(chord.noteNameFromChordMember(chordMemberList.get(2)), lead);
		result.add(note);
		
		int tenor = placeAbove(chord.chromaticIndexFromChordMember(chordMemberList.get(3)), lead);
		note = new NoteBean(chord.noteNameFromChordMember(chordMemberList.get(3)), tenor);
		result.add(note);
		
		return result;
	}
}
