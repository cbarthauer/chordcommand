package music.chord.engine.protocol;

import java.util.Arrays;
import java.util.List;

import music.chord.arrangement.VoicedChord;
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
    
    public final ChordRequest chordRequest(VoicedChord... chords) {
        return chordRequest(Arrays.asList(chords));
    }
    
    public final ChordRequest chordRequest(List<VoicedChord> chordList) {
        return new ChordRequestImpl(identifier, chordList);
    }
    
    public final DurationRequest durationRequest(Integer... positions) {
        return durationRequest(Arrays.asList(positions));
    }

    public final DurationRequest durationRequest(List<Integer> positions) {
        return new DurationRequestImpl(identifier, positions, duration);
    }

    public final InsertChordRequest insertRequest(int position, VoicedChord... chords) {
        return insertRequest(position, Arrays.asList(chords));
    }
    
    public final InsertChordRequest insertRequest(int position, List<VoicedChord> chordList) {
        return new InsertChordRequestImpl(
            new ChordRequestImpl(identifier, chordList), 
            position);
    }
    
    public final LoadRequest loadRequest(String fileName) {
        LoadRequest request = new LoadRequestImpl(fileName, identifier);
        return request;
    }

    public final OctaveRequest octaveRequest(Integer... positions) {
        return octaveRequest(Arrays.asList(positions));
    }

    public final OctaveRequest octaveRequest(List<Integer> positions) {
        return new OctaveRequestImpl(identifier, positions, octave);
    }
    
    public final RemoveChordRequest removeRequest(Integer... position) {
        return removeRequest(Arrays.asList(position));
    }

    public final RemoveChordRequest removeRequest(List<Integer> positionList) {
        return new RemoveChordRequestImpl(identifier, positionList);
    }

    public final void setDuration(Duration duration) {
        this.duration = duration;
    }

    public final void setOctave(int octave) {
        this.octave = octave;
    }

    public final void setVoicing(Voicing voicing) {
        this.voicing = voicing;
    }
    
    public final VoiceAllRequest voiceAllRequest() {
        return new VoiceAllRequestImpl(identifier);
    }

    public final VoicingRequest voicingRequest(Integer... positions) {
        return voicingRequest(Arrays.asList(positions));
    }

    public final VoicingRequest voicingRequest(List<Integer> positions) {
        return new VoicingRequestImpl(identifier, positions, voicing);
    }
}
