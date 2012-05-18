package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.decorator.Chord;

public interface VoicedChord extends Chord {
	public int difference(VoicedChord chord);
	public Duration getDuration();
	public List<Integer> getMidiNumberList();
	public List<Note> getNoteBeanList();
	public int getOctave();
	public int getTicks(int ppq);
	public Voicing getVoicing();
}
