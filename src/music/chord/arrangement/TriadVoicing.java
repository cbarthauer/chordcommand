package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;

public class TriadVoicing extends AbstractVoicing {
    private List<ChordMember> validMemberList;
    
    public TriadVoicing() {
        super();
        validMemberList = new ArrayList<ChordMember>();
        validMemberList.add(ChordMember.ROOT);
        validMemberList.add(ChordMember.THIRD);
        validMemberList.add(ChordMember.FIFTH);
    }
    
	@Override
	void validateChordMember(ChordMember member) {
		if(!(validMemberList.contains(member))) {
			throw new IllegalArgumentException(
					"Invalid ChordMember for TriadVoicing: " + member);
		}
	}
}
