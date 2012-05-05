package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.decorator.Chord;
import music.chord.decorator.NoteBean;

public interface Voicing {
	public List<NoteBean> voice(Chord chord);
	public void addChordMember(ChordMember member);
}
