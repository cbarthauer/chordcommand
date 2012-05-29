package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;
import music.chord.decorator.Chord;


public class VoicingImpl implements Voicing {
	
    private List<ChordMember> chordMemberList;
    private VoicingValidator validator;
	
	
    public VoicingImpl(VoicingValidator validator) {
		chordMemberList = new ArrayList<ChordMember>();
		this.validator = validator;
	}
	
	public final void addChordMember(ChordMember member) {
		validator.validateChordMember(member);
		chordMemberList.add(member);
	}
	
	@Override
    public boolean contains(ChordMember member) {
        return chordMemberList.contains(member);
    }

	public final String toString() {
		return chordMemberList.toString();
	}

	public final List<Note> voice(Chord chord, int octave, Duration duration) {
		if(chord == null) throw new IllegalArgumentException("Chord cannot be null.");
		
		NoteListBuilder builder = new NoteListBuilder();
		
		int midiNumber = -1;
		
		for(ChordMember chordMember : chordMemberList) {
			NoteName noteName = chord.noteNameFromChordMember(chordMember);
			midiNumber = placeAbove(noteName.getChromaticIndex(), midiNumber);
			builder.add(noteName, midiNumber, duration);
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
}
