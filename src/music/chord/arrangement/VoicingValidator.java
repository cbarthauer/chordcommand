package music.chord.arrangement;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;

public class VoicingValidator {
    public final static VoicingValidator TRIAD_VALIDATOR;
    public final static VoicingValidator SEVENTH_VALIDATOR;
    public final static VoicingValidator NINTH_VALIDATOR;
    
    static {
        List<ChordMember> triadList = new ArrayList<ChordMember>();
        triadList.add(ChordMember.ROOT);
        triadList.add(ChordMember.THIRD);
        triadList.add(ChordMember.FIFTH);
        TRIAD_VALIDATOR = new VoicingValidator(triadList);
        
        List<ChordMember> seventhList = new ArrayList<ChordMember>();
        seventhList.add(ChordMember.ROOT);
        seventhList.add(ChordMember.THIRD);
        seventhList.add(ChordMember.FIFTH);
        seventhList.add(ChordMember.SEVENTH);
        SEVENTH_VALIDATOR = new VoicingValidator(seventhList);
        
        List<ChordMember> ninthList = new ArrayList<ChordMember>();
        ninthList.add(ChordMember.ROOT);
        ninthList.add(ChordMember.THIRD);
        ninthList.add(ChordMember.FIFTH);
        ninthList.add(ChordMember.SEVENTH);
        ninthList.add(ChordMember.NINTH);
        NINTH_VALIDATOR = new VoicingValidator(ninthList);
    }
    
    private List<ChordMember> validMemberList;
    
    public VoicingValidator(List<ChordMember> validMemberList) {
        this.validMemberList = new ArrayList<ChordMember>(validMemberList);
    }
    
    public void validateChordMember(ChordMember member) {
        if(!(validMemberList.contains(member))) {
            throw new IllegalArgumentException(
                    "Invalid ChordMember " + validMemberList + ": " + member);
        }
    }
}
