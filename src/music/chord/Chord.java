package music.chord;

public class Chord {
	private NoteName rootName;
	private Quality thirdQuality;
	private Interval thirdInterval;
	private Interval fifthInterval;
	
	public Chord(NoteName rootName, Quality thirdQuality) {
		this.rootName = rootName;
		this.thirdQuality = thirdQuality;
		
		switch(thirdQuality) {
		case MAJOR:
			this.thirdInterval = Interval.MAJOR_THIRD;
			break;
		case MINOR:
			this.thirdInterval = Interval.MINOR_THIRD;
			break;
		default:
			throw new RuntimeException("Unrecognized third quality: " + thirdQuality);
		}
		
		fifthInterval = Interval.PERFECT_FIFTH;
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
		return "chord(" + rootName.toString() + " " + thirdQuality.toString() + "): {\n" + 
	           "  root: " + rootName.toString() + ",\n" + 
			   "  third: " + getThirdNoteName().toString() + "\n" +
	           "  fifth: " + getFifthNoteName().toString() + "\n" +
	           "}\n";
	}
}
