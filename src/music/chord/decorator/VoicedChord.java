package music.chord.decorator;

import java.util.List;

public interface VoicedChord extends Chord {
	public Duration getDuration();
	public List<Integer> getMidiNumberList();
	public List<NoteBean> getNoteBeanList();
	public int getTicks(int ppq);
	public Voicing getVoicing();
	public int difference(VoicedChord chord);
}
