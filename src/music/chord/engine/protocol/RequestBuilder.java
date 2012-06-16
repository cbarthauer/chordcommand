package music.chord.engine.protocol;

import java.util.Arrays;

import music.chord.base.NoteName;
import music.chord.base.Quality;

public final class RequestBuilder {

    private Identifier identifier;
    private int insertPosition;

    public RequestBuilder(Identifier identifier) {
        this.identifier = identifier;
        this.insertPosition = 0;
    }

    public final AddChordRequest addRequest(String noteSymbol, Quality quality) {
        NoteName noteName = NoteName.forSymbol(noteSymbol);
        return new AddChordRequestImpl(identifier, noteName, quality);
    }

    public final InsertChordRequest insertRequest(
            String noteSymbol,
            Quality quality) {
        
        NoteName noteName = NoteName.forSymbol(noteSymbol);
        return new InsertChordRequestImpl(identifier, noteName, quality, insertPosition);
    }

    public final RemoveChordRequest removeRequest(Integer... position) {
        return new RemoveChordRequestImpl(identifier, Arrays.asList(position));
    }

    public final void setInsertPosition(int insertPosition) {
        this.insertPosition = insertPosition;
    }

}
