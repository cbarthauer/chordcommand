package music.chord.decorator;

public class ChordBuilder {
	private NoteName root;
	private Quality triadQuality;
	private Quality seventhQuality;
	
	public Chord build() {
		Chord chord = new Triad(root, triadQuality);
		
		if(seventhQuality != null) {
			chord = new SeventhChord((Triad) chord, seventhQuality);
		}
		
		reset();
		
		return chord;
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
	
	private void reset() {
		root = null;
		triadQuality = null;
		seventhQuality = null;
	}
}
