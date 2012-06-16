package music.chord.engine.protocol;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

final class AddChordRequestImpl implements AddChordRequest {

    private Identifier identifier;
    private NoteName noteName;
    private Quality quality;

    AddChordRequestImpl(
            Identifier identifier, 
            NoteName noteName,
            Quality quality) {

        this.identifier = identifier;
        this.noteName = noteName;
        this.quality = quality;
    }

    @Override
    public final Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public final NoteName getNoteName() {
        return noteName;
    }

    @Override
    public final Quality getQuality() {
        return quality;
    }

    @Override
    public ChordType getType() {
        return quality.getType();
    }

}
