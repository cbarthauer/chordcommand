package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.ChordPair;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public final class QualityVoicedChordBuilder implements VoicedChordBuilder {

    private VoicedChordConfig defaultConfig;
    private VoicedChordConfig currentConfig;    
    
    public QualityVoicedChordBuilder(VoicedChordConfig chordConfig) {
        this.defaultConfig = chordConfig;
        this.currentConfig = chordConfig;
    }
    
    @Override
    public final VoicedChord buildVoicedChord() {        
        VoicedChord result =
            new VoicedChordImpl(
                currentConfig.getRoot(),
                currentConfig.getQuality(),
                currentConfig.getVoicing(),
                currentConfig.getOctave(),
                currentConfig.getDuration(),
                currentConfig.getPartList());
        
        reset();
        return result;
    }
    
    @Override
    public final QualityVoicedChordBuilder setChord(VoicedChord chord) {
        currentConfig = new VoicedChordConfig(
            chord.noteNameFromChordMember(ChordMember.ROOT),
            chord.getQuality(),
            chord.getVoicing(),
            chord.getOctave(),
            chord.getDuration(),
            chord.getVoicePartList());
        
        return this;
    }
    
    @Override
    public final QualityVoicedChordBuilder setDuration(Duration duration) {
        currentConfig = new VoicedChordConfig(duration, currentConfig);
        return this;
    }
    
    @Override
    public final QualityVoicedChordBuilder setOctave(int octave) {
        currentConfig = new VoicedChordConfig(octave, currentConfig);
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setPair(ChordPair pair) {
        throw new RuntimeException("setPair() not implemented.");
    }
    
    @Override
    public final VoicedChordBuilder setQuality(Quality quality) {
        currentConfig = new VoicedChordConfig(quality, currentConfig);
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setRoot(NoteName root) {
        currentConfig = new VoicedChordConfig(root, currentConfig);
        return this;
    }

    @Override
    public final QualityVoicedChordBuilder setVoicePartList(List<VoicePart> partList) {
        currentConfig = new VoicedChordConfig(partList, currentConfig);
        return this;
    }

    @Override
    public final QualityVoicedChordBuilder setVoicing(Voicing voicing) {
        currentConfig = new VoicedChordConfig(voicing, currentConfig);
        return this;
    }

    private void reset() {
        currentConfig = defaultConfig;
    }

}
