package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;
import music.chord.base.VoicePart;

final class VoicedChordConfig {
    
    private Voicing voicing;
    private int octave;
    private Duration duration;
    private List<VoicePart> partList;
    
    VoicedChordConfig(Duration duration, VoicedChordConfig config) {
        this(config.voicing, config.octave, duration, config.partList);
    }
    
    VoicedChordConfig(int octave, VoicedChordConfig config) {
        this(config.voicing, octave, config.duration, config.partList);
    }

    VoicedChordConfig(List<VoicePart> partList, VoicedChordConfig config) {
        this(config.voicing, config.octave, config.duration, partList);
    }

    VoicedChordConfig(
            Voicing voicing, 
            int octave, 
            Duration duration, 
            List<VoicePart> partList) {
        
        this.voicing = voicing;
        this.octave = octave;
        this.duration = duration;
        this.partList = new ArrayList<VoicePart>(partList);
    }

    VoicedChordConfig(Voicing voicing, VoicedChordConfig config) {
        this(voicing, config.octave, config.duration, config.partList);
    }

    final Duration getDuration() {
        return duration;
    }
    
    final int getOctave() {
        return octave;
    }
    
    final List<VoicePart> getPartList() {
        return new ArrayList<VoicePart>(partList);
    }

    final Voicing getVoicing() {
        return voicing;
    }
}
