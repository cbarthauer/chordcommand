package music.chord.engine.protocol;

import java.util.List;

final class InsertChordRequestImpl implements InsertChordRequest {

    private ChordRequest chordRequest;
    private int position;

    public InsertChordRequestImpl(
            ChordRequest chordRequest, 
            int position) {

        this.chordRequest = chordRequest;
        this.position = position;
    }

    @Override
    public List<ChordPair> getChordPairs() {
        return chordRequest.getChordPairs();
    }

    @Override
    public Identifier getIdentifier() {
        return chordRequest.getIdentifier();
    }

    @Override
    public int getPosition() {
        return position;
    }

}
