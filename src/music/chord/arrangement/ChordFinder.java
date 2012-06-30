package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public interface ChordFinder {

    public List<VoicedChord> find(NoteName note, ChordMember member);

    public List<VoicedChord> find(List<NoteName> noteList);

}