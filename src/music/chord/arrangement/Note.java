package music.chord.arrangement;

import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.NoteName;


public class Note implements Comparable<Note> {
	private NoteName noteName;
	private Integer midiNumber;
    private Duration duration;
    private int octave;

    Note(NoteName noteName, Integer midiNumber, Duration duration) {
		this.noteName = noteName;
		this.midiNumber = midiNumber;
		this.duration = duration;
	}

	@Override
	public int compareTo(Note otherNote) {
		return midiNumber.compareTo(otherNote.midiNumber);
	}

    public Duration getDuration() {
        return duration;
    }

	public Integer getMidiNumber() {
		return midiNumber;
	}

	public NoteName getNoteName() {
		return noteName;
	}

	public int getOctave() {
        return octave;
    }
	
	public int getTicks(int ppq) {
        float conversionFactor = duration.getPpqConversionFactor();
        return Math.round(ppq * conversionFactor);
    }

	public String toString() {
		return noteName + "(" + midiNumber + ")";
	}

    void setOctave(int octave) {
	    this.octave = octave;
		midiNumber = midiNumber + (octave * Interval.PERFECT_OCTAVE.getHalfSteps());
	}
}
