package music.chord.decorator;


public class Triad extends ForwardingChord {
	private Interval thirdInterval;
	private Interval fifthInterval;
	private Quality triadQuality;
	
	public Triad(NoteName rootName, Quality triadQuality) {
		super(new BasicChord(rootName));
		
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
			throw new RuntimeException(
				"Unrecognized triad quality: " + triadQuality);
		}
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		NoteName result = null;
		
		switch(chordMember) {
		case ROOT:
			result = super.noteNameFromChordMember(chordMember);
			break;
			
		case THIRD:
			result = getThirdNoteName();
			break;
			
		case FIFTH:
			result = getFifthNoteName();
			break;
			
		default:
			throw new IllegalArgumentException(
				"Unknown chord member: " + chordMember);
		}
		return result;
	}

	public String toString() {
		return super.toString() + " " + triadQuality;
	}
	
	private NoteName getFifthNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(fifthInterval);
	}
	
	private NoteName getThirdNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(thirdInterval);		
	}	
}
