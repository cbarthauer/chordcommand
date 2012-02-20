package music.chord.decorator;

public class ChordBuilder {
	private Chord chord;
	
	public ChordBuilder setRoot(NoteName root) {
		chord = new BasicChord(root);
		return this;
	}
	
	public ChordBuilder setTriadQuality(Quality quality) {
		chord = new Triad(chord.noteNameFromChordMember(ChordMember.ROOT), quality);
		return this;
	}
	
	public Chord build() {
		return chord;
	}
}
