package music.chord.decorator;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;


public interface Chord {
	public NoteName noteNameFromChordMember(ChordMember chordMember);
}
