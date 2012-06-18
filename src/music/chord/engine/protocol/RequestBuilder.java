package music.chord.engine.protocol;

import java.util.Arrays;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.LoadRequest;

public final class RequestBuilder {

    private Identifier identifier;
    private int insertPosition;
    private Voicing voicing;
    private Duration duration;

    public RequestBuilder(Identifier identifier) {
        this(identifier, 0);
    }
    
    public RequestBuilder(Identifier identifier, int insertPosition) {
        this.identifier = identifier;
        this.insertPosition = insertPosition;
        this.voicing = null;
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
    
    public DurationRequest durationRequest(Integer... positions) {
        return durationRequest(Arrays.asList(positions));
    }

    public DurationRequest durationRequest(List<Integer> positions) {
        return new DurationRequestImpl(identifier, positions, duration);
    }

    public final InsertChordRequest insertRequest(
            String noteSymbol,
            Quality quality) {
        
        NoteName noteName = NoteName.forSymbol(noteSymbol);
        return new InsertChordRequestImpl(identifier, noteName, quality, insertPosition);
    }
    
    public final InsertChordRequest[] insertRequests(List<VoicedChord> chordList) {
        InsertChordRequest[] requests = new InsertChordRequest[chordList.size()];
        
        for(int i = 0; i < requests.length; i++) {
            VoicedChord chord = chordList.get(i);
            requests[i] = insertRequest(
                chord.noteNameFromChordMember(ChordMember.ROOT).getSymbol(),
                chord.getQuality());
        }
        
        return requests;
    }

    public LoadRequest loadRequest(String fileName) {
        LoadRequest request = new LoadRequestImpl(fileName, identifier);
        return request;
    }

    public final RemoveChordRequest removeRequest(Integer... position) {
        return removeRequest(Arrays.asList(position));
    }

    public final RemoveChordRequest removeRequest(List<Integer> positionList) {
        return new RemoveChordRequestImpl(identifier, positionList);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    
    public final void setInsertPosition(int insertPosition) {
        this.insertPosition = insertPosition;
    }

    public void setVoicing(Voicing voicing) {
        this.voicing = voicing;
    }

    public VoicingRequest voicingRequest(Integer... positions) {
        return voicingRequest(Arrays.asList(positions));
    }

    public VoicingRequest voicingRequest(List<Integer> positions) {
        return new VoicingRequestImpl(identifier, positions, voicing);
    }
}
