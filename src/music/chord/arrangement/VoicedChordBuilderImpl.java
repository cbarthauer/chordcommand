package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.ChordPair;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;

public final class VoicedChordBuilderImpl implements VoicedChordBuilder {

    private VoicedChordConfig defaultConfig;
    private VoicedChordConfig currentConfig;
    private ChordPair pair;
    private ChordDefinitionStructure struct;
    
    public VoicedChordBuilderImpl(
            VoicedChordConfig chordConfig,
            ChordDefinitionStructure struct) {
        
        this.defaultConfig = chordConfig;
        this.currentConfig = chordConfig;        
        this.struct = struct;
    }
    
    @Override
    public final VoicedChord buildVoicedChord() {
        VoicedChord result = 
            new ConcreteChord(
                buildChord(), 
                currentConfig.getVoicing(), 
                currentConfig.getOctave(), 
                currentConfig.getDuration(), 
                currentConfig.getPartList(),
                pair.getQualityEnum());
        
        reset();
        return result;
    }
    
    @Override
    public final VoicedChordBuilder setChord(VoicedChord chord) {
        currentConfig = new VoicedChordConfig(
            null,
            null,
            chord.getVoicing(),
            chord.getOctave(),
            chord.getDuration(),
            chord.getVoicePartList());
        
        pair = new ChordPair(
            chord.noteNameFromChordMember(ChordMember.ROOT), 
            chord.getQualityEnum());
        
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setDuration(Duration duration) {
        currentConfig = new VoicedChordConfig(duration, currentConfig);
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setOctave(int octave) {
        currentConfig = new VoicedChordConfig(octave, currentConfig);
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setPair(ChordPair pair) {
        this.pair = pair;
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setQuality(Quality quality) {
        throw new RuntimeException("Not implemented.");
    }
    
    @Override
    public final VoicedChordBuilder setRoot(NoteName noteName) {
        throw new RuntimeException("Not implemented.");
    }
    
    @Override
    public final VoicedChordBuilder setVoicePartList(List<VoicePart> partList) {
        currentConfig = new VoicedChordConfig(partList, currentConfig);
        return this;
    }
    
    @Override
    public final VoicedChordBuilder setVoicing(Voicing voicing) {
        currentConfig = new VoicedChordConfig(voicing, currentConfig);
        return this;
    }

    private Chord buildChord() {
        return new ChordImpl(pair.getRoot(), struct.getChordSpec(pair.getQualityEnum()));
    }

    private void reset() {
        currentConfig = defaultConfig;
    }

}
