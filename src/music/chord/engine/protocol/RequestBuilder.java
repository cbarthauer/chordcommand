package music.chord.engine.protocol;

import java.util.Arrays;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.base.ChordMember;
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

    public final AddChordRequest[] addRequests(List<VoicedChord> chordList) {
        AddChordRequest[] requests = new AddChordRequest[chordList.size()];
        
        for(int i = 0; i < requests.length; i++) {
            VoicedChord chord = chordList.get(i);
            requests[i] = addRequest(
                chord.noteNameFromChordMember(ChordMember.ROOT).getSymbol(),
                chord.getQuality());
        }
        
        return requests;
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
