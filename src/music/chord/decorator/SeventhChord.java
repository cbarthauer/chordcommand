package music.chord.decorator;

import music.chord.decorator.ChordMember;
import music.chord.decorator.Interval;
import music.chord.decorator.NoteName;
import music.chord.decorator.Quality;

public class SeventhChord extends ForwardingChord {
	private Quality seventhQuality;
	private Interval seventhInterval;

	public SeventhChord(Triad triad, Quality seventhQuality) {
		super(triad);
		this.seventhQuality = seventhQuality;
		
		switch(seventhQuality) {
		case MAJOR:
			seventhInterval = Interval.MAJOR_SEVENTH;
			break;
		case MINOR:
			seventhInterval = Interval.MINOR_SEVENTH;
			break;
		case DIMINISHED:
			seventhInterval = Interval.DIMINISHED_SEVENTH;
			break;
		default:
			throw new RuntimeException("Unrecognized seventh quality: " + seventhQuality);
		}
	}

	public NoteName getSeventhNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(seventhInterval);
	}
	
	public Quality getSeventhQuality() {
		return seventhQuality;
	}
	
	@Override
	public NoteName noteNameFromChordMember(ChordMember chordMember) {
		NoteName result = null;
		
		switch(chordMember) {
		case ROOT:
		case THIRD:
		case FIFTH:
			result = super.noteNameFromChordMember(chordMember);
			break;
			
		case SEVENTH:
			result = getSeventhNoteName();
			break;		
			
		default:
			throw new IllegalArgumentException(
				"Unknown chord member: " + chordMember);
		}
		return result;
	}
}