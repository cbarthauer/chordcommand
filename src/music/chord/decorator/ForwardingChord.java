package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.NoteName;

public class ForwardingChord implements Chord {

	private final Chord chord;
	
	public ForwardingChord(Chord chord) {
		this.chord = chord;
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		return chord.noteNameFromChordMember(chordMember);
	}

}
