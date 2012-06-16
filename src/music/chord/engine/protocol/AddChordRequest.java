package music.chord.engine.protocol;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public interface AddChordRequest {
    public Identifier getIdentifier();
    public NoteName getNoteName();
    public Quality getQuality();
    public ChordType getType();
}
