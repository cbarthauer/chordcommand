package music.chord.builder;

import music.chord.arrangement.ConcreteChord;
import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;
import music.chord.base.TriadQuality;
import music.chord.decorator.SeventhChord;

public class SeventhBuilder implements ChordBuilder {
	private Interval seventhInterval;
	private Duration currentDuration;
	private Voicing currentVoicing;
	private TriadBuilder triadBuilder;
    private Voicing defaultVoicing;
    private Duration defaultDuration;
    private int defaultOctave;
    private int currentOctave;
	
	public SeventhBuilder(
	        TriadBuilder triadBuilder, 
	        SeventhVoicing defaultVoicing, 
	        int defaultOctave,
	        Duration defaultDuration) {
	    
	    this.triadBuilder = triadBuilder;
	    
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
		    new ConcreteChord(buildSeventhChord(), currentVoicing, currentOctave, currentDuration);
		reset();
		return result;
	}
	
	public SeventhBuilder setDuration(Duration duration) {
		triadBuilder.setDuration(duration);
		return this;
	}
	
	public SeventhBuilder setOctave(int octave) {
	    this.currentOctave = octave;
	    return this;
	}
	
	public SeventhBuilder setRoot(NoteName root) {
		triadBuilder.setRoot(root);
		return this;
	}

	public SeventhBuilder setSeventhInterval(Interval seventhInterval) {
		this.seventhInterval = seventhInterval;
		return this;
	}

	public SeventhBuilder setTriadQuality(TriadQuality triadQuality) {
		triadBuilder.setTriadQuality(triadQuality);
		return this;
	}
	
	public SeventhBuilder setVoicing(Voicing voicing) {
		this.currentVoicing = voicing;
		return this;
	}
	
	private SeventhChord buildSeventhChord() {
		return new SeventhChord(triadBuilder.buildTriad(), seventhInterval);
	}
	
	private void reset() {
	    seventhInterval = null;
		currentDuration = defaultDuration;
		currentVoicing = defaultVoicing;
		currentOctave = defaultOctave;
	}
}
