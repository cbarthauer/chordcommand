package music.chord.engine.protocol;

import java.util.Arrays;
import java.util.List;

import music.chord.arrangement.Voicing;
import music.chord.base.Duration;

public final class RequestBuilder {

    private Identifier identifier;
    private Voicing voicing;
    private Duration duration;
    private int octave;

    public RequestBuilder(Identifier identifier) {
        this.identifier = identifier;
    }
    
    public ChordRequest chordRequest(ChordPair... chordPairs) {
        return chordRequest(Arrays.asList(chordPairs));
    }
    
    public ChordRequest chordRequest(List<ChordPair> chordPairs) {
        return new ChordRequestImpl(identifier, chordPairs);
    }
    
    public DurationRequest durationRequest(Integer... positions) {
        return durationRequest(Arrays.asList(positions));
    }

    public DurationRequest durationRequest(List<Integer> positions) {
        return new DurationRequestImpl(identifier, positions, duration);
    }

    public InsertChordRequest insertRequest(int position, ChordPair... chordPairs) {
        return insertRequest(position, Arrays.asList(chordPairs));
    }
    
    public InsertChordRequest insertRequest(int position, List<ChordPair> chordPairs) {
        return new InsertChordRequestImpl(
            new ChordRequestImpl(identifier, chordPairs), 
            position);
    }
    
    public LoadRequest loadRequest(String fileName) {
        LoadRequest request = new LoadRequestImpl(fileName, identifier);
        return request;
    }

    public OctaveRequest octaveRequest(Integer... positions) {
        return octaveRequest(Arrays.asList(positions));
    }

    public OctaveRequest octaveRequest(List<Integer> positions) {
        return new OctaveRequestImpl(identifier, positions, octave);
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

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public void setVoicing(Voicing voicing) {
        this.voicing = voicing;
    }
    
    public VoiceAllRequest voiceAllRequest() {
        return new VoiceAllRequestImpl(identifier);
    }

    public VoicingRequest voicingRequest(Integer... positions) {
        return voicingRequest(Arrays.asList(positions));
    }

    public VoicingRequest voicingRequest(List<Integer> positions) {
        return new VoicingRequestImpl(identifier, positions, voicing);
    }
}
