package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;

public final class ChordRequestImpl implements ChordRequest {

    private Identifier id;
    private List<VoicedChord> chordList;

    public ChordRequestImpl(Identifier id, List<VoicedChord> chordList) {
        this.id = id;
        this.chordList = new ArrayList<VoicedChord>(chordList);
    }

    public final List<VoicedChord> getChordList() {
        return new ArrayList<VoicedChord>(chordList);
    }

    public final Identifier getIdentifier() {
        return id;
    }

}
