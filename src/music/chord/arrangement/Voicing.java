package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.decorator.Chord;

public interface Voicing {
	public List<Note> voice(Chord chord, int octave, Duration duration);
	public void addChordMember(ChordMember member);
	public boolean contains(ChordMember member);
}
