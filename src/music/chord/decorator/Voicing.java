package music.chord.decorator;

import java.util.ArrayList;
import java.util.List;

import music.chord.decorator.ChordMember;
import music.chord.decorator.NoteBean;
import music.chord.decorator.NoteName;

public class Voicing extends AbstractVoicing {
	private static final int octaveShift = 48;
	
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
		
		int chromaticIndex = -1;
		
		for(ChordMember chordMember : chordMemberList) {
			NoteName noteName = chord.noteNameFromChordMember(chordMember);
			chromaticIndex = placeAbove(noteName.getChromaticIndex(), chromaticIndex);
			NoteBean note = new NoteBean(noteName, chromaticIndex);
			System.out.println("Voicing.voice() - Adding note: " + note);
			result.add(note);
		}
		
		result = shiftUp(result, octaveShift);	
		
		return result;
	}
	
	private List<NoteBean> shiftUp(List<NoteBean> noteBeanList, int numberHalfSteps) {
		for(NoteBean noteBean : noteBeanList) {
			noteBean.setMidiNumber(noteBean.getMidiNumber() + numberHalfSteps);
		}
		
		return noteBeanList;
	}
}
