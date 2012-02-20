package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.NoteName;

public interface Chord {
	public NoteName noteNameFromChordMember(ChordMember chordMember);
}
