package music.chord.arrangement;

import java.util.List;

import music.chord.base.NoteName;
import music.chord.engine.protocol.filter.ChordMemberFilter;

public interface ChordFinder {
    public List<VoicedChord> find(ChordMemberFilter filter);
    public List<VoicedChord> find(List<NoteName> noteList);
}