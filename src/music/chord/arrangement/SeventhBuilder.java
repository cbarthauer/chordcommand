package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.SeventhQuality;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;

public class SeventhBuilder implements ChordBuilder {
	private SeventhQuality seventhQuality;
	private Duration currentDuration;
	private Voicing currentVoicing;
    private Voicing defaultVoicing;
    private Duration defaultDuration;
    private int defaultOctave;
    private int currentOctave;
    private List<VoicePart> defaultPartList;
    private List<VoicePart> currentPartList;
    private String symbol;
    private NoteName root;
	
	public SeventhBuilder(
	        SeventhVoicing defaultVoicing, 
	        int defaultOctave,
	        Duration defaultDuration,
	        List<VoicePart> defaultPartList) {
	    
	    this.defaultVoicing = defaultVoicing;	
	    this.currentVoicing = defaultVoicing;
	    this.defaultDuration = defaultDuration;
	    this.currentDuration = defaultDuration;		
		this.defaultOctave = defaultOctave;
		this.currentOctave = defaultOctave;
		this.defaultPartList = defaultPartList;
		this.currentPartList = defaultPartList;
		this.symbol = "";
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
	
	public SeventhBuilder setDuration(Duration duration) {
		this.currentDuration = duration;
		return this;
	}
	
	public SeventhBuilder setOctave(int octave) {
	    this.currentOctave = octave;
	    return this;
	}
	
	public SeventhBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}

	public SeventhBuilder setSeventhQuality(SeventhQuality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}

	public SeventhBuilder setSymbol(String symbol) {
	    this.symbol = symbol;
	    return this;
	}
	
	public SeventhBuilder setVoicing(Voicing voicing) {
		this.currentVoicing = voicing;
		return this;
	}
	
	private Chord buildChord() {
		return new ChordImpl(root, seventhQuality.getChordSpec());
	}
	
	private void reset() {
	    seventhQuality = null;
		currentDuration = defaultDuration;
		currentVoicing = defaultVoicing;
		currentOctave = defaultOctave;
		currentPartList = defaultPartList;
	}
}
