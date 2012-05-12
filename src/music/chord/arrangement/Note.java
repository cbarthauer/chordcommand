package music.chord.arrangement;

import music.chord.base.NoteName;


public class Note implements Comparable<Note> {
	private static final int HALF_STEPS_IN_OCTAVE = 12;
	private NoteName noteName;
	private Integer midiNumber;

	public Note(NoteName noteName, Integer midiNumber) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
	}

	@Override
	public int compareTo(Note otherNote) {
		return midiNumber.compareTo(otherNote.midiNumber);
	}

	public Integer getMidiNumber() {
		return midiNumber;
	}

	public NoteName getNoteName() {
		return noteName;
	}
	
	public void setOctave(int octave) {
		midiNumber = midiNumber + (octave * HALF_STEPS_IN_OCTAVE);
	}

	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}
}
