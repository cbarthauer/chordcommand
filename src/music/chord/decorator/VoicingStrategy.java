package music.chord.decorator;

import java.util.List;

import music.chord.decorator.NoteBean;

public interface VoicingStrategy {
	public List<NoteBean> voice(Chord chord);
}
