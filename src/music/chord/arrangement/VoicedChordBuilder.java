package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordSpec;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;

public class VoicedChordBuilder implements ChordBuilder {

    private ChordSpec currentChordSpec;
    private ChordSpec defaultChordSpec;
    private Duration currentDuration;
    private Duration defaultDuration;
    private Voicing currentVoicing;
    private Voicing defaultVoicing;
    private int currentOctave;
    private int defaultOctave;
    private List<VoicePart> currentPartList;
    private List<VoicePart> defaultPartList;
    private String symbol;
    private NoteName root;
    
    
    public VoicedChordBuilder(
            ChordSpec defaultChordSpec,
            Voicing defaultVoicing,
            int defaultOctave,
            Duration defaultDuration,
            List<VoicePart> defaultPartList) {
        
        this.defaultChordSpec = defaultChordSpec;
        this.defaultVoicing = defaultVoicing;
        this.defaultOctave = defaultOctave;
        this.defaultDuration = defaultDuration;
        this.defaultPartList = defaultPartList;
        
        this.currentChordSpec = defaultChordSpec;
        this.currentVoicing = defaultVoicing;
        this.currentOctave = defaultOctave;
        this.currentDuration = defaultDuration;
        this.currentPartList = defaultPartList;
    }
    
    @Override
    public VoicedChord buildVoicedChord() {
        VoicedChord result = 
            new ConcreteChord(
                buildChord(), 
                currentVoicing, 
                currentOctave, 
                currentDuration, 
                currentPartList,
                symbol);
        reset();
        return result;
    }
    
    public VoicedChordBuilder setChord(VoicedChord chord) {
        this.currentDuration = chord.getDuration();
        this.currentVoicing = chord.getVoicing();
        this.currentOctave = chord.getOctave();
        this.currentPartList = chord.getVoicePartList();
        this.symbol = chord.getSymbol();
        return this;
    }
    
    public VoicedChordBuilder setChordSpec(ChordSpec spec) {
        this.currentChordSpec = spec;
        return this;
    }
    
    public VoicedChordBuilder setDuration(Duration duration) {
        this.currentDuration = duration;
        return this;
    }
    
    public VoicedChordBuilder setOctave(int octave) {
        this.currentOctave = octave;
        return this;
    }
    
    public VoicedChordBuilder setRoot(NoteName root) {
        this.root = root;
        return this;
    }

    public VoicedChordBuilder setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }
    
    public VoicedChordBuilder setVoicePartList(List<VoicePart> partList) {
        partList = new ArrayList<VoicePart>(partList);
        return this;
    }
    
    public VoicedChordBuilder setVoicing(Voicing voicing) {
        this.currentVoicing = voicing;
        return this;
    }
    
    private Chord buildChord() {
        return new ChordImpl(root, currentChordSpec);
    }
    
    private void reset() {
        currentChordSpec = defaultChordSpec;
        currentVoicing = defaultVoicing;
        currentOctave = defaultOctave;
        currentDuration = defaultDuration;
        currentPartList = defaultPartList;
    }

}
