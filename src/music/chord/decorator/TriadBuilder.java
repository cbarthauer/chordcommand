package music.chord.decorator;


public class TriadBuilder implements ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Duration duration;
	private Voicing voicing;
	
	public TriadBuilder() {
		voicing = Voicing.getInstance();
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.FIFTH);
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.THIRD);
	}
	
	@Override
	public VoicedChord build() {
		VoicedChord resultChord = null;
		Chord chord = new Triad(root, triadQuality);
		resultChord = new ConcreteChord(chord, voicing, duration);
		reset();
		return resultChord;
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
}
