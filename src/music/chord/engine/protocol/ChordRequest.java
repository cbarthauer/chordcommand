package music.chord.engine.protocol;

import java.util.List;

import music.chord.arrangement.VoicedChord;

public interface ChordRequest {
    public List<VoicedChord> getChordList();
    public Identifier getIdentifier();
}
