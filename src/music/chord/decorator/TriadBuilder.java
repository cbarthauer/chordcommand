package music.chord.decorator;

import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;


public class TriadBuilder implements ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Duration duration;
	private Voicing voicing;
	
	public TriadBuilder() {
		voicing = new TriadVoicing();
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.FIFTH);
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.THIRD);
		
		duration = Duration.QUARTER;
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

	public TriadBuilder setTriadQuality(Quality triadQuality) {
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
		duration = null;
		voicing = null;
	}
	
	Triad buildTriad() {
		return new Triad(root, triadQuality);
	}
}
