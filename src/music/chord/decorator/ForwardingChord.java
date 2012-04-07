package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.NoteName;

public class ForwardingChord implements Chord {

	private final Chord chord;
	
	public ForwardingChord(Chord chord) {
		this.chord = chord;
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		System.out.println("ForwardingChord.noteNameFromChordMember() - chord: " + chord);
		System.out.println("ForwardingChord.noteNameFromChordMember() - chordMember: " + chordMember);
		return chord.noteNameFromChordMember(chordMember);
	}
	
	@Override
	public String toString() {
		return chord.toString();
	}

}
