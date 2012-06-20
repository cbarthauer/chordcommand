package music.chord.engine.protocol;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

public final class ChordPair {
    
    private NoteName root;
    private Quality quality;

    public ChordPair(NoteName root, Quality quality) {
        this.root = root;
        this.quality = quality;
    }
    
    public final Quality getQuality() {
        return quality;
    }

    public ChordType getType() {
        return quality.getType();
    }

    public NoteName getRoot() {
        return root;
    }
}
