package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;

class VoicingFactory {
    private static final int DEFAULT_OCTAVE_SHIFT = 4;
    
    static Voicing instanceFromChordMemberList(
            List<ChordMember> chordMemberList) {
        
        Voicing voicing = null;
        
        if(chordMemberList.contains(ChordMember.SEVENTH)) {
            voicing = new SeventhVoicing(DEFAULT_OCTAVE_SHIFT);
        }
        else {
            voicing = new TriadVoicing(DEFAULT_OCTAVE_SHIFT);
        }  
      
        for(ChordMember currentMember : chordMemberList) {
            voicing.addChordMember(currentMember);
        }       
        
        return voicing;
    }

    private VoicingFactory() {
        //Hide constructor in static factory.
    }
}
