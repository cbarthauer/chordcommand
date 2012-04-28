package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;


public abstract class AbstractVoicing implements Voicing {
	private static final int octaveShift = 4;
	
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

	public final List<NoteBean> voice(Chord chord) {
		if(chord == null) throw new IllegalArgumentException("Chord cannot be null.");
		
		List<NoteBean> result = new ArrayList<NoteBean>();
		
		int chromaticIndex = -1;
		
		for(ChordMember chordMember : chordMemberList) {
			NoteName noteName = chord.noteNameFromChordMember(chordMember);
			chromaticIndex = placeAbove(noteName.getChromaticIndex(), chromaticIndex);
			NoteBean note = new NoteBean(noteName, chromaticIndex);
			result.add(note);
		}
		
		result = shiftUp(result, octaveShift);	
		
		return result;
	}
	
	private List<NoteBean> shiftUp(List<NoteBean> noteBeanList, int octave) {
		for(NoteBean noteBean : noteBeanList) {
			noteBean.setOctave(octave);
		}
		
		return noteBeanList;
	}

	protected final int placeAbove(int upper, int lower) {
		int result = upper;
		
		while(result <= lower) {
			result = result + 12;
		}
		
		return result;
	}
	
	abstract void validateChordMember(ChordMember member);
}
