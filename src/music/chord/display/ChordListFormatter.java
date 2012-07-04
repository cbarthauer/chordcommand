package music.chord.display;

import java.util.List;

import music.chord.arrangement.VoicedChord;

public interface ChordListFormatter {
    public String format(List<VoicedChord> chordList);
}
