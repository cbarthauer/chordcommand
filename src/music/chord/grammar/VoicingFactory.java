package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;

class VoicingFactory {
    static Voicing instanceFromChordMemberList(
            List<ChordMember> chordMemberList) {
        
        Voicing voicing = null;
        
        if(chordMemberList.contains(ChordMember.SEVENTH)) {
            voicing = new SeventhVoicing(4);
        }
        else {
            voicing = new TriadVoicing(4);
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
