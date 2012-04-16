package music.chord.decorator;

public class TriadVoicing extends AbstractVoicing {
	@Override
	void validateChordMember(ChordMember member) {
		if(!(ChordMember.ROOT.equals(member)
		|| ChordMember.THIRD.equals(member)
		|| ChordMember.FIFTH.equals(member))) {
			throw new IllegalArgumentException(
					"Invalid ChordMember for TriadVoicing: " + member);
		}
	}
}
