package music.chord.arrangement;

import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;
import music.chord.engine.protocol.ChordPair;

public final class VoicedChordBuilder implements ChordBuilder {

    private VoicedChordConfig defaultConfig;
    private VoicedChordConfig currentConfig;
    private ChordPair pair;
    private ChordDefinitionStructure struct;
    
    public VoicedChordBuilder(
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
                pair.getQuality());
        reset();
        return result;
    }
    
    public final VoicedChordBuilder setChord(VoicedChord chord) {
        currentConfig = new VoicedChordConfig(
            chord.getVoicing(),
            chord.getOctave(),
            chord.getDuration(),
            chord.getVoicePartList());
        
        pair = new ChordPair(
            chord.noteNameFromChordMember(ChordMember.ROOT), 
            chord.getQuality());
        
        return this;
    }
    
    public final VoicedChordBuilder setDuration(Duration duration) {
        currentConfig = new VoicedChordConfig(duration, currentConfig);
        return this;
    }
    
    public final VoicedChordBuilder setOctave(int octave) {
        currentConfig = new VoicedChordConfig(octave, currentConfig);
        return this;
    }
    
    public final VoicedChordBuilder setPair(ChordPair pair) {
        this.pair = pair;
        return this;
    }
    
    public final VoicedChordBuilder setVoicePartList(List<VoicePart> partList) {
        currentConfig = new VoicedChordConfig(partList, currentConfig);
        return this;
    }
    
    public final VoicedChordBuilder setVoicing(Voicing voicing) {
        currentConfig = new VoicedChordConfig(voicing, currentConfig);
        return this;
    }
    
    private Chord buildChord() {
        return new ChordImpl(pair.getRoot(), struct.getChordSpec(pair.getQuality()));
    }
    
    private void reset() {
        currentConfig = defaultConfig;
    }

}
