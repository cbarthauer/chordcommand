package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.decorator.Chord;
import music.chord.decorator.NoteBean;

public interface VoicedChord extends Chord {
	public Duration getDuration();
	public List<Integer> getMidiNumberList();
	public List<NoteBean> getNoteBeanList();
	public int getTicks(int ppq);
	public Voicing getVoicing();
	public int difference(VoicedChord chord);
}
