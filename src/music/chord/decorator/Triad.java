package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.Interval;
import music.chord.decorator.NoteName;
import music.chord.decorator.Quality;

public class Triad extends ForwardingChord {
	private Quality triadQuality;
	private Interval thirdInterval;
	private Interval fifthInterval;
	
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
	
	public NoteName getFifthNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(fifthInterval);
	}

	public NoteName getThirdNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(thirdInterval);		
	}
	
	public Quality getTriadQuality() {
		return triadQuality;
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
}
