package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

final class VoicedChordConfig {
    
    private NoteName root;
    private Quality quality;
    private Voicing voicing;
    private int octave;
    private Duration duration;
    private List<VoicePart> partList;
    
    public VoicedChordConfig(NoteName root, VoicedChordConfig config) {
        this(
            root, 
            config.quality, 
            config.voicing, 
            config.octave, 
            config.duration, 
            config.partList);  
    }

    public VoicedChordConfig(Quality quality, VoicedChordConfig config) {
        this(
            config.root, 
            quality, 
            config.voicing, 
            config.octave, 
            config.duration, 
            config.partList);        
    }
    
    VoicedChordConfig(Duration duration, VoicedChordConfig config) {
        this(
            config.root, 
            config.quality, 
            config.voicing, 
            config.octave, 
            duration, 
            config.partList);
    }

    VoicedChordConfig(int octave, VoicedChordConfig config) {
        this(
            config.root, 
            config.quality, 
            config.voicing, 
            octave, 
            config.duration, 
            config.partList);
    }

    VoicedChordConfig(List<VoicePart> partList, VoicedChordConfig config) {
        this(
            config.root, 
            config.quality, 
            config.voicing, 
            config.octave, 
            config.duration, 
            partList);
    }

    VoicedChordConfig(
            NoteName root,
            Quality quality,
            Voicing voicing, 
            int octave, 
            Duration duration, 
            List<VoicePart> partList) {
        
        this.root = root;
        this.quality = quality;
        this.voicing = voicing;
        this.octave = octave;
        this.duration = duration;
        this.partList = new ArrayList<VoicePart>(partList);
    }

    VoicedChordConfig(Voicing voicing, VoicedChordConfig config) {
        this(
            config.root, 
            config.quality, 
            voicing, 
            config.octave, 
            config.duration, 
            config.partList);
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
    
    final Quality getQuality() {
        return quality;
    }
    
    final NoteName getRoot() {
        return root;
    }

    final Voicing getVoicing() {
        return voicing;
    }
}
