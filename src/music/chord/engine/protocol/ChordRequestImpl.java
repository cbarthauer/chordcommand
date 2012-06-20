package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

public final class ChordRequestImpl implements ChordRequest {

    private Identifier id;
    private List<ChordPair> chordPairs;

    public ChordRequestImpl(Identifier id, List<ChordPair> chordPairs) {
        this.id = id;
        this.chordPairs = new ArrayList<ChordPair>(chordPairs);
    }

    public final List<ChordPair> getChordPairs() {
        return new ArrayList<ChordPair>(chordPairs);
    }

    public final Identifier getIdentifier() {
        return id;
    }

}
