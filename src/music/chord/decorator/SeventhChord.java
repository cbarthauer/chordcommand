package music.chord.decorator;

import music.chord.base.ChordMember;
import music.chord.base.Interval;
import music.chord.base.NoteName;


public class SeventhChord extends ForwardingChord {
	private Interval seventhInterval;

	public SeventhChord(Triad triad, Interval seventhInterval) {
		super(triad);
		this.seventhInterval = seventhInterval;
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
	
	public String toString() {
		return super.toString() + " " + seventhInterval;
	}
	
	private NoteName getSeventhNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(seventhInterval);
	}
}
