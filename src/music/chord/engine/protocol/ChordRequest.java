package music.chord.engine.protocol;

import java.util.List;

public interface ChordRequest {
    public List<ChordPair> getChordPairs();
    public Identifier getIdentifier();
}
