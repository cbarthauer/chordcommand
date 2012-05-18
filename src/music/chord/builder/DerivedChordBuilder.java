package music.chord.builder;

import music.chord.arrangement.ConcreteChord;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.Duration;

public class DerivedChordBuilder implements ChordBuilder {

	private VoicedChord chord;
	private Duration duration;
	private Voicing voicing;
    private int octave;

	@Override
	public VoicedChord buildVoicedChord() {
		return new ConcreteChord(chord, voicing, octave, duration);
	}

	public DerivedChordBuilder setChord(VoicedChord chord) {
		this.chord = chord;
		this.duration = chord.getDuration();
		this.voicing = chord.getVoicing();
		this.octave = chord.getOctave();
		return this;
	}
	
	public DerivedChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
	
	public DerivedChordBuilder setOctave(int octave) {
	    this.octave = octave;
	    return this;
	}
	
	public DerivedChordBuilder setVoicing(Voicing voicing) {
		this.voicing = voicing;
		return this;
	}
}
