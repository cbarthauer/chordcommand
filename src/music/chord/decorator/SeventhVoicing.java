package music.chord.decorator;


public class SeventhVoicing extends AbstractVoicing {
	
	public static Voicing getInstance() {
		return new SeventhVoicing();
	}
	
	@Override
	void validateChordMember(ChordMember member) {
		if(!(ChordMember.ROOT.equals(member)
		|| ChordMember.THIRD.equals(member)
		|| ChordMember.FIFTH.equals(member)
		|| ChordMember.SEVENTH.equals(member))) {
			throw new IllegalArgumentException(
					"Invalid ChordMember for TriadVoicing: " + member);
		}
	}

}
