package music.chord;

import java.util.List;

public interface VoicingStrategy {
	public List<NoteBean> voice(Chord chord);
}
