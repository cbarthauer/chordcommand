package music.chord.grammar;

import java.util.List;

import music.chord.arrangement.NinthVoicing;
import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;

class VoicingFactory {
    static Voicing instanceFromChordMemberList(
            List<ChordMember> chordMemberList) {
        
        Voicing voicing = null;
        
        if(chordMemberList.contains(ChordMember.NINTH)) {
            voicing = new NinthVoicing();
        }
        else if(chordMemberList.contains(ChordMember.SEVENTH)) {
            voicing = new SeventhVoicing();
        }
        else {
            voicing = new TriadVoicing();
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
