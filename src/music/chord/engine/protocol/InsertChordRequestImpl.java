package music.chord.engine.protocol;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

final class InsertChordRequestImpl implements InsertChordRequest {

    private Identifier identifier;
    private NoteName noteName;
    private Quality quality;
    private int position;

    public InsertChordRequestImpl(
            Identifier identifier, 
            NoteName noteName,
            Quality quality, 
            int position) {

        this.identifier = identifier;
        this.noteName = noteName;
        this.quality = quality;
        this.position = position;
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
    public final int getPosition() {
        return position;
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
