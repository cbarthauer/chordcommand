package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public final class QualityVoicedChordBuilder implements ChordBuilder {

    private VoicedChordConfig defaultConfig;
    private VoicedChordConfig currentConfig;
    private NoteName root;
    private Quality quality;
    
    
    public QualityVoicedChordBuilder(VoicedChordConfig chordConfig) {
        this.defaultConfig = chordConfig;
        this.currentConfig = chordConfig;
    }
    
    @Override
    public final VoicedChord buildVoicedChord() {        
        VoicedChord result =
            new VoicedChordImpl(
                root,
                quality,
                currentConfig.getVoicing(),
                currentConfig.getOctave(),
                currentConfig.getDuration(),
                currentConfig.getPartList());
        
        reset();
        return result;
    }
    
    public final QualityVoicedChordBuilder setChord(VoicedChord chord) {
        currentConfig = new VoicedChordConfig(
            chord.getVoicing(),
            chord.getOctave(),
            chord.getDuration(),
            chord.getVoicePartList());
        
        root = chord.noteNameFromChordMember(ChordMember.ROOT);
        quality = chord.getQuality();
        return this;
    }
    
    public final QualityVoicedChordBuilder setDuration(Duration duration) {
        currentConfig = new VoicedChordConfig(duration, currentConfig);
        return this;
    }
    
    public final QualityVoicedChordBuilder setOctave(int octave) {
        currentConfig = new VoicedChordConfig(octave, currentConfig);
        return this;
    }
    
    public final QualityVoicedChordBuilder setVoicePartList(List<VoicePart> partList) {
        currentConfig = new VoicedChordConfig(partList, currentConfig);
        return this;
    }
    
    public final QualityVoicedChordBuilder setVoicing(Voicing voicing) {
        currentConfig = new VoicedChordConfig(voicing, currentConfig);
        return this;
    }
    
    private void reset() {
        currentConfig = defaultConfig;
    }

}
