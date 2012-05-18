package music.chord.arrangement;

import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.TriadQuality;
import music.chord.decorator.Triad;


public class TriadBuilder implements ChordBuilder {
	private NoteName root;
	private TriadQuality triadQuality;
	private Duration currentDuration;
	private Voicing currentVoicing;
    private Voicing defaultVoicing;
    private Duration defaultDuration;
    private int defaultOctave;
    private int currentOctave;
	
	public TriadBuilder(
	        TriadVoicing defaultVoicing,
	        int defaultOctave,
	        Duration defaultDuration) {
	    
	    this.defaultVoicing = defaultVoicing;
	    this.currentVoicing = defaultVoicing;
		this.defaultDuration = defaultDuration;
		this.currentDuration = defaultDuration;
		this.defaultOctave = defaultOctave;
		this.currentOctave = defaultOctave;
	}
	
	@Override
	public VoicedChord buildVoicedChord() {
		VoicedChord result = 
		    new ConcreteChord(buildTriad(), currentVoicing, currentOctave, currentDuration);
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
	}
	
	Triad buildTriad() {
		return new Triad(root, triadQuality);
	}
}
