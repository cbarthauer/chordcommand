package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;


public class SeventhVoicing extends AbstractVoicing {
    private List<ChordMember> validMemberList;
    
    public SeventhVoicing(int octaveShift) {
        super(octaveShift);
        validMemberList = new ArrayList<ChordMember>();
        validMemberList.add(ChordMember.ROOT);
        validMemberList.add(ChordMember.THIRD);
        validMemberList.add(ChordMember.FIFTH);
        validMemberList.add(ChordMember.SEVENTH);
    }
    
	@Override
	void validateChordMember(ChordMember member) {
		if(!(validMemberList.contains(member))) {
			throw new IllegalArgumentException(
					"Invalid ChordMember for SeventhVoicing: " + member);
		}
	}
}
