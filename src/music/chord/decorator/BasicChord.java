package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.NoteName;

public class BasicChord implements Chord {
	private NoteName rootNoteName;
	
	public BasicChord(NoteName rootNoteName) {
		this.rootNoteName = rootNoteName;
	}

	public NoteName getRootNoteName() {
		return rootNoteName;
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		NoteName result = null;
		
		switch(chordMember) {
		case ROOT:
			result = getRootNoteName();
			break;
		default:
			throw new IllegalArgumentException(
				"Unknown chord member: " + chordMember);
		}
		return result;
	}

}
