package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Interval;
import music.chord.base.NoteName;
import music.chord.decorator.Chord;


abstract class AbstractVoicing implements Voicing {
	
    private List<ChordMember> chordMemberList;
	
	
	AbstractVoicing() {
		chordMemberList = new ArrayList<ChordMember>();
	}
	
	public final void addChordMember(ChordMember member) {
		validateChordMember(member);
		chordMemberList.add(member);
	}
	
	public final String toString() {
		return chordMemberList.toString();
	}

	public final List<Note> voice(Chord chord, int octave) {
		if(chord == null) throw new IllegalArgumentException("Chord cannot be null.");
		
		NoteListBuilder builder = new NoteListBuilder();
		
		int midiNumber = -1;
		
		for(ChordMember chordMember : chordMemberList) {
			NoteName noteName = chord.noteNameFromChordMember(chordMember);
			midiNumber = placeAbove(noteName.getChromaticIndex(), midiNumber);
			builder.add(noteName, midiNumber);
		}
		
		builder.shiftUp(octave);	
		
		return builder.getNoteList();
	}

	private int placeAbove(int upper, int lower) {
		int result = upper;
		
		while(result <= lower) {
			result = result + Interval.PERFECT_OCTAVE.getHalfSteps();
		}
		
		return result;
	}
	
	abstract void validateChordMember(ChordMember member);
}
