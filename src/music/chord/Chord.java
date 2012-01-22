package music.chord;

public class Chord {
	private NoteName rootName;
	private Quality triadQuality;
	private Interval thirdInterval;
	private Interval fifthInterval;
	
	public Chord(NoteName rootName, Quality triadQuality) {
		this.rootName = rootName;
		this.triadQuality = triadQuality;
		
		switch(triadQuality) {
		case AUGMENTED:
			this.thirdInterval = Interval.MAJOR_THIRD;
			this.fifthInterval = Interval.AUGMENTED_FIFTH;
			break;			
		case MAJOR:
			this.thirdInterval = Interval.MAJOR_THIRD;
			this.fifthInterval = Interval.PERFECT_FIFTH;
			break;
		case MINOR:
			this.thirdInterval = Interval.MINOR_THIRD;
			this.fifthInterval = Interval.PERFECT_FIFTH;
			break;
		case DIMINISHED:
			this.thirdInterval = Interval.MINOR_THIRD;
			this.fifthInterval = Interval.DIMINISHED_FIFTH;
			break;
		default:
			throw new RuntimeException("Unrecognized triad quality: " + triadQuality);
		}
		
		
	}
	
	public NoteName getFifthNoteName() {
		return rootName.up(fifthInterval);
	}
	
	public NoteName getRootNoteName() {
		return rootName;
	}

	public NoteName getThirdNoteName() {
		return rootName.up(thirdInterval);		
	}

	public String toString() {
		return "chord(" + rootName.toString() + " " + triadQuality.toString() + "): {\n" + 
	           "  root: " + rootName.toString() + ",\n" + 
			   "  third: " + getThirdNoteName().toString() + "\n" +
	           "  fifth: " + getFifthNoteName().toString() + "\n" +
	           "}\n";
	}

	public int chromaticIndexFromChordMember(ChordMember chordMember) {
		int result = 0;
		
		switch(chordMember) {
		case ROOT:
			result = getRootNoteName().getChromaticIndex();
			break;
		case THIRD:
			result = getThirdNoteName().getChromaticIndex();
			break;
		case FIFTH:
			result = getFifthNoteName().getChromaticIndex();
			break;
		default:
			throw new RuntimeException("Unknown chord member: " + chordMember);
		}
		
		return result;
	}
}
