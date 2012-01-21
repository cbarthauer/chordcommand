package music.chord;

import java.util.List;

public interface VoicingStrategy {
	public List<Integer> voice(Chord chord);
}
