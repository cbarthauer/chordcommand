package music.chord.decorator;

public class DerivedChordBuilder implements ChordBuilder {

	private VoicedChord chord;
	private Duration duration;
	private Voicing voicing;

	@Override
	public VoicedChord build() {
		return new ConcreteChord(chord, voicing, duration);
	}

	public DerivedChordBuilder setChord(VoicedChord chord) {
		this.chord = chord;
		this.duration = chord.getDuration();
		this.voicing = chord.getVoicing();
		return this;
	}
	
	public DerivedChordBuilder setVoicing(Voicing voicing) {
		this.voicing = voicing;
		return this;
	}
	
	public DerivedChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
}