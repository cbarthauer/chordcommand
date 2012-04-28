package music.chord.base;

public enum Interval {
//	                   chromaticHalfSteps diatonicSteps
	MINOR_THIRD(       3,                 2),
	MAJOR_THIRD(       4,                 2),
	DIMINISHED_FIFTH(  6,                 4),
	PERFECT_FIFTH(     7,                 4),
	AUGMENTED_FIFTH(   8,                 4),
	MAJOR_SIXTH(       9,                 5),
	DIMINISHED_SEVENTH(9,                 6),
	MINOR_SEVENTH(     10,                6),
	MAJOR_SEVENTH(     11,                6);
	
	private int halfSteps;
	private int diatonicSteps;

	private Interval(int halfSteps, int diatonicSteps) {
		this.halfSteps = halfSteps;
		this.diatonicSteps = diatonicSteps;
	}
	
	public int getDiatonicSteps() {
		return diatonicSteps;
	}

	public int getHalfSteps() {
		return halfSteps;
	}
}
