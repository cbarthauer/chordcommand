package music.chord.arrangement;

import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;


public class Note implements Comparable<Note> {
	private NoteName noteName;
	private Integer midiNumber;
    private Duration duration;

	Note(NoteName noteName, Integer midiNumber, Duration duration) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
		this.duration = duration;
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
	
	void setOctave(int octave) {
		midiNumber = midiNumber + (octave * Interval.PERFECT_OCTAVE.getHalfSteps());
	}

	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}

    public int getTicks(int ppq) {
        float conversionFactor = duration.getPpqConversionFactor();
        return Math.round(ppq * conversionFactor);
    }
}
