package music.chord;

public enum Interval {
//	              chromaticHalfSteps diatonicSteps
	MINOR_THIRD(  3,                 2),
	MAJOR_THIRD(  4,                 2), 
	PERFECT_FIFTH(7,                 4);
	
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
