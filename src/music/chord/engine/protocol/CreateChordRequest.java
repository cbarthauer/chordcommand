package music.chord.engine.protocol;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public interface CreateChordRequest {
    Quality getQuality();
    NoteName getRoot();
    ChordType getType();
}
