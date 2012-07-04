package music.chord.engine.protocol;

import java.util.List;

import music.chord.arrangement.VoicedChord;

final class InsertChordRequestImpl implements InsertChordRequest {

    private ChordRequest chordRequest;
    private int position;

    InsertChordRequestImpl(
            ChordRequest chordRequest, 
            int position) {

        this.chordRequest = chordRequest;
        this.position = position;
    }

    @Override
    public final List<VoicedChord> getChordList() {
        return chordRequest.getChordList();
    }

    @Override
    public final Identifier getIdentifier() {
        return chordRequest.getIdentifier();
    }

    @Override
    public final int getPosition() {
        return position;
    }

}
