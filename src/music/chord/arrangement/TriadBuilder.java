package music.chord.arrangement;

import java.util.List;

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.TriadQuality;
import music.chord.base.VoicePart;
import music.chord.decorator.Chord;
import music.chord.decorator.ChordImpl;


public class TriadBuilder implements ChordBuilder {
	private NoteName root;
	private TriadQuality triadQuality;
	private Duration currentDuration;
    private Duration defaultDuration;
	private Voicing currentVoicing;
    private Voicing defaultVoicing;
    private int defaultOctave;
    private int currentOctave;
    private List<VoicePart> defaultPartList;
    private List<VoicePart> currentPartList;
    private String symbol;
	
	public TriadBuilder(
	        TriadVoicing defaultVoicing,
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

	public TriadBuilder setDuration(Duration duration) {
		this.currentDuration = duration;
		return this;
	}
	
	public TriadBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}

	public TriadBuilder setSymbol(String symbol) {
	    this.symbol = symbol;
	    return this;
	}
	
	public TriadBuilder setTriadQuality(TriadQuality triadQuality) {
		this.triadQuality = triadQuality;
		return this;
	}

	public TriadBuilder setVoicing(Voicing voicing) {
		this.currentVoicing = voicing;
		return this;
	}
	
	private void reset() {
		root = null;
		triadQuality = null;
		currentDuration = defaultDuration;
		currentVoicing = defaultVoicing;
		currentOctave = defaultOctave;
        currentPartList = defaultPartList;
        this.symbol = "";
	}
	
	Chord buildChord() {
	    return new ChordImpl(root, triadQuality.getChordSpec());
	}
}
