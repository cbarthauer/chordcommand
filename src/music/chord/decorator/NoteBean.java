package music.chord.decorator;

import music.chord.base.NoteName;


public class NoteBean implements Comparable<NoteBean> {
	private static final int HALF_STEPS_IN_OCTAVE = 12;
	private NoteName noteName;
	private Integer midiNumber;

	public NoteBean(NoteName noteName, Integer midiNumber) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
	}

	@Override
	public int compareTo(NoteBean otherNote) {
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
