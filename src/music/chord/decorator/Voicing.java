package music.chord.decorator;

import java.util.List;

import music.chord.base.ChordMember;

public interface Voicing {
	public List<NoteBean> voice(Chord chord);
	public void addChordMember(ChordMember member);
}
