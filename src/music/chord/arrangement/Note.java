package music.chord.arrangement;

import music.chord.base.Interval;
import music.chord.base.NoteName;


public class Note implements Comparable<Note> {
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
		midiNumber = midiNumber + (octave * Interval.PERFECT_OCTAVE.getHalfSteps());
	}

	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}
}
