package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public interface VoicedChordBuilder {
    public VoicedChord buildVoicedChord();
    public VoicedChordBuilder setChord(VoicedChord chord);
    public VoicedChordBuilder setDuration(Duration duration);
    public VoicedChordBuilder setOctave(int octave);
    public VoicedChordBuilder setQuality(Quality quality);
    public VoicedChordBuilder setRoot(NoteName noteName);
    public VoicedChordBuilder setVoicePartList(List<VoicePart> partList);
    public VoicedChordBuilder setVoicing(Voicing voicing);
}
