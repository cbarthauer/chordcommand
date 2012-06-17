package music.chord.grammar;

import java.util.Arrays;
import java.util.List;

import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingImpl;
import music.chord.arrangement.VoicingValidator;
import music.chord.base.ChordMember;

public class VoicingFactory {
    public static Voicing instanceFromChordMemberList(
            List<ChordMember> chordMemberList) {
        
        Voicing voicing = null;
        
        if(chordMemberList.contains(ChordMember.NINTH)) {
            voicing = new VoicingImpl(VoicingValidator.NINTH_VALIDATOR);
        }
        else if(chordMemberList.contains(ChordMember.SEVENTH)) {
            voicing = new VoicingImpl(VoicingValidator.SEVENTH_VALIDATOR);
        }
        else {
            voicing = new VoicingImpl(VoicingValidator.TRIAD_VALIDATOR);
        }  
      
        for(ChordMember currentMember : chordMemberList) {
            voicing.addChordMember(currentMember);
        }       
        
        return voicing;
    }

    public static Voicing instanceFromChordMemberList(ChordMember... members) {
        return instanceFromChordMemberList(Arrays.asList(members));
    }
    
    private VoicingFactory() {
        //Hide constructor in static factory.
    }
}
