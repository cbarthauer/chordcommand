package music.chord.main;

import music.chord.arrangement.SeventhVoicing;
import music.chord.arrangement.TriadVoicing;
import music.chord.base.ChordMember;
import music.chord.builder.SeventhBuilder;
import music.chord.builder.TriadBuilder;

class BuilderFactory {

    private static final int defaultOctaveShift = 4;
    
    static SeventhBuilder getSeventhBuilder(TriadBuilder triadBuilder) {
        SeventhVoicing seventhVoicing = new SeventhVoicing(defaultOctaveShift);
        seventhVoicing.addChordMember(ChordMember.ROOT);
        seventhVoicing.addChordMember(ChordMember.FIFTH);
        seventhVoicing.addChordMember(ChordMember.SEVENTH);
        seventhVoicing.addChordMember(ChordMember.THIRD);
        SeventhBuilder builder = new SeventhBuilder(triadBuilder, seventhVoicing);
        
        return builder;
    }

    static TriadBuilder getTriadBuilder() {
        TriadVoicing triadVoicing = new TriadVoicing(defaultOctaveShift);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.FIFTH);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.THIRD);
        TriadBuilder triadBuilder = new TriadBuilder(triadVoicing);
        
        return triadBuilder;
    }    
    
    private BuilderFactory() {
        //Hide constructor for static factory.
    }
}