package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;

public interface VoicedChord extends Chord {
	public int difference(VoicedChord chord);
	public Duration getDuration();
	public List<Integer> getMidiNumberList();
	public List<Note> getNoteList();
	public int getOctave();
	public int getTicks(int ppq);
	public List<VoicePart> getVoicePartList();
	public Voicing getVoicing();
	public Note noteFromVoicePart(VoicePart part);
}
