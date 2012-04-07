package music.chord.decorator;

public class ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Quality seventhQuality;
	private Duration duration;
	private Voicing voicing;
	
	public Chord build() {
		Chord chord = new Triad(root, triadQuality);
		
		if(seventhQuality != null) {
			chord = new SeventhChord((Triad) chord, seventhQuality);
		}
		
		if(voicing != null) {
			chord = new VoicedChord(chord, voicing);
			
			if(duration != null) {
				chord = new RealizedChord((VoicedChord) chord, duration);
			}
		}
				
		reset();
		
		return chord;
	}
	
	public ChordBuilder setDuration(Duration duration) {
		this.duration = duration;
		return this;
	}
	
	public ChordBuilder setRoot(NoteName root) {
		this.root = root;
		return this;
	}

	public ChordBuilder setSeventhQuality(Quality seventhQuality) {
		this.seventhQuality = seventhQuality;
		return this;
	}
	
	public ChordBuilder setTriadQuality(Quality triadQuality) {
		this.triadQuality = triadQuality;
		return this;
	}
	
	public ChordBuilder setVoicing(Voicing voicing) {
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
