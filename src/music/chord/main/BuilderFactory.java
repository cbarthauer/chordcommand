package music.chord.main;

import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.SeventhQuality;
import music.chord.base.TriadQuality;
import music.chord.base.VoicePart;

class BuilderFactory {

    private static final int defaultOctaveShift = 4;
    
    static VoicedChordBuilder getSeventhBuilder() {
        SeventhVoicing seventhVoicing = new SeventhVoicing();
        seventhVoicing.addChordMember(ChordMember.ROOT);
        seventhVoicing.addChordMember(ChordMember.FIFTH);
        seventhVoicing.addChordMember(ChordMember.SEVENTH);
        seventhVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder builder = new VoicedChordBuilder(
            SeventhQuality.DOMINANT.getChordSpec(), 
            seventhVoicing, 
            defaultOctaveShift, 
            Duration.QUARTER,
            VoicePart.barbershopDefault());
        
        return builder;
    }   
    
    static VoicedChordBuilder getTriadBuilder() {
        TriadVoicing triadVoicing = new TriadVoicing();
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.FIFTH);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder triadBuilder = new VoicedChordBuilder(
            TriadQuality.MAJOR.getChordSpec(), 
            triadVoicing,
            defaultOctaveShift, 
            Duration.QUARTER, 
            VoicePart.barbershopDefault());
      
        return triadBuilder;
    }

    private BuilderFactory() {
        //Hide constructor for static factory.
    }
}