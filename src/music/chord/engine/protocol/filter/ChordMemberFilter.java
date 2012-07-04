package music.chord.engine.protocol.filter;

import music.chord.arrangement.VoicedChord;

public interface ChordMemberFilter {
    public boolean filter(VoicedChord chord);
}
