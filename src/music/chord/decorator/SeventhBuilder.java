package music.chord.decorator;

public class SeventhBuilder implements ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Quality seventhQuality;
	private Duration duration;
	private Voicing voicing;
	
	public SeventhBuilder() {
		voicing = Voicing.getInstance();
		voicing.addChordMember(ChordMember.ROOT);
		voicing.addChordMember(ChordMember.FIFTH);
		voicing.addChordMember(ChordMember.SEVENTH);
		voicing.addChordMember(ChordMember.THIRD);
	}
	
	@Override
	public VoicedChord build() {
		VoicedChord resultChord = null;
		Chord chord = new SeventhChord(
			new Triad(root, triadQuality), 
			seventhQuality
		);
		resultChord = new ConcreteChord(chord, voicing, duration);
		reset();
		return resultChord;
	}
	
	public SeventhBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}

	public SeventhBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}

	public SeventhBuilder setSeventhQuality(Quality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}
	
	public SeventhBuilder setTriadQuality(Quality triadQuality) {
		this.triadQuality = triadQuality;
		return this;
	}
	
	public SeventhBuilder setVoicing(Voicing voicing) {
		this.voicing = voicing;
		return this;
	}
	
	private void reset() {
		root = null;
		triadQuality = null;
		seventhQuality = null;
		duration = null;
		voicing = null;
	}
}
