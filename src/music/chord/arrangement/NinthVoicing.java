package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;

public class NinthVoicing extends AbstractVoicing implements Voicing {

    private List<ChordMember> validMemberList;
    
    public NinthVoicing() {
        super();
        validMemberList = new ArrayList<ChordMember>();
        validMemberList.add(ChordMember.ROOT);
        validMemberList.add(ChordMember.THIRD);
        validMemberList.add(ChordMember.FIFTH);
        validMemberList.add(ChordMember.SEVENTH);
        validMemberList.add(ChordMember.NINTH);
    }
    
    @Override
    void validateChordMember(ChordMember member) {
        if(!(validMemberList.contains(member))) {
            throw new IllegalArgumentException(
                    "Invalid ChordMember for NinthVoicing: " + member);
        }
    }

}
