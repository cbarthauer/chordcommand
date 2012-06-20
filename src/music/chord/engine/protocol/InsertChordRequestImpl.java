package music.chord.engine.protocol;

import java.util.List;

import music.chord.arrangement.VoicedChord;

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
    public List<VoicedChord> getChordList() {
        return chordRequest.getChordList();
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
