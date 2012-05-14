package music.chord.builder;

import music.chord.arrangement.ConcreteChord;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.TriadQuality;
import music.chord.decorator.Triad;


public class TriadBuilder implements ChordBuilder {
	private NoteName root;
	private TriadQuality triadQuality;
	private Duration duration;
	private Voicing voicing;
    private Voicing defaultVoicing;
    private Duration defaultDuration;
	
	public TriadBuilder(TriadVoicing defaultVoicing) {
	    this.defaultVoicing = defaultVoicing;
	    this.voicing = defaultVoicing;
		this.defaultDuration = Duration.QUARTER;
		this.duration = defaultDuration;
	}
	
	@Override
	public VoicedChord buildVoicedChord() {
		VoicedChord result = new ConcreteChord(buildTriad(), voicing, duration);
		reset();
		return result;
	}

	public TriadBuilder setDuration(Duration duration) {
		this.duration = duration;
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
		this.voicing = voicing;
		return this;
	}
	
	private void reset() {
		root = null;
		triadQuality = null;
		duration = defaultDuration;
		voicing = defaultVoicing;
	}
	
	Triad buildTriad() {
		return new Triad(root, triadQuality);
	}
}
