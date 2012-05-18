package music.chord.arrangement;

import music.chord.base.Interval;
import music.chord.base.NoteName;


class Note implements Comparable<Note> {
	private NoteName noteName;
	private Integer midiNumber;

	Note(NoteName noteName, Integer midiNumber) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
	}

	@Override
	public int compareTo(Note otherNote) {
		return midiNumber.compareTo(otherNote.midiNumber);
	}

	Integer getMidiNumber() {
		return midiNumber;
	}

	NoteName getNoteName() {
		return noteName;
	}
	
	void setOctave(int octave) {
		midiNumber = midiNumber + (octave * Interval.PERFECT_OCTAVE.getHalfSteps());
	}

	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}
}
