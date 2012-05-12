package music.chord.decorator;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;
import music.chord.base.TriadQuality;


public class Triad extends ForwardingChord {
	private TriadQuality triadQuality;
	
	public Triad(NoteName rootName, TriadQuality triadQuality) {
		super(new BasicChord(rootName));		
		this.triadQuality = triadQuality;
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
			.up(triadQuality.getFifthInterval());
	}
	
	private NoteName getThirdNoteName() {
		return noteNameFromChordMember(ChordMember.ROOT)
			.up(triadQuality.getThirdInterval());		
	}	
}
