package music.chord.decorator;

import java.util.List;

public interface VoicedChord extends Chord {
	public List<Integer> getMidiNumberList();
	public List<NoteBean> getNoteBeanList();
	public int getTicks(int ppq);
	public int difference(VoicedChord chord);
}
