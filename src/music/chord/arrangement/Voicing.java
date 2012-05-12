package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.decorator.Chord;

public interface Voicing {
	public List<Note> voice(Chord chord);
	public void addChordMember(ChordMember member);
}
