package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;


public final class VoicingImpl implements Voicing {
	
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
    public final boolean contains(ChordMember member) {
        return chordMemberList.contains(member);
    }

	@Override
    public final List<ChordMember> getChordMembers() {
        return new ArrayList<ChordMember>(chordMemberList);
    }

	@Override
    public final boolean isCongruentTo(Voicing voicing) {
        List<ChordMember> otherMembers = voicing.getChordMembers();
        return chordMemberList.containsAll(otherMembers) 
                && otherMembers.containsAll(chordMemberList);
    }

	@Override
    public final String toString() {
		return chordMemberList.toString()
		        .toLowerCase();
	}

	@Override
    public final List<Note> voice(VoicedChord chord, int octave, Duration duration) {
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
		int result = upper - 1;
		
		while(result <= lower) {
			result = result + Interval.PERFECT_OCTAVE.getHalfSteps();
		}
		
		return result;
	}
}
