package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;

public interface Voicing {
	public void addChordMember(ChordMember member);
	public boolean contains(ChordMember member);
	public List<ChordMember> getChordMembers();
	public boolean isCongruentTo(Voicing voicing);
    public List<Note> voice(VoicedChord chord, int octave, Duration duration);
}
