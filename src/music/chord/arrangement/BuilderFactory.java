package music.chord.arrangement;

import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.Quality;
import music.chord.base.VoicePart;

public class BuilderFactory {

    private static final int defaultOctaveShift = 4;
    
    public static VoicedChordBuilder getNinthBuilder(ChordDefinitionStructure struct) {
        Voicing ninthVoicing = new VoicingImpl(VoicingValidator.NINTH_VALIDATOR);
        ninthVoicing.addChordMember(ChordMember.ROOT);
        ninthVoicing.addChordMember(ChordMember.SEVENTH);
        ninthVoicing.addChordMember(ChordMember.NINTH);
        ninthVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder ninthBuilder = new VoicedChordBuilder(
            struct.getChordSpec("Ninth", Quality.DOMINANT),
            ninthVoicing,
            defaultOctaveShift,
            Duration.QUARTER,
            VoicePart.barbershopDefault());
        return ninthBuilder;
    }   
    
    public static VoicedChordBuilder getSeventhBuilder(ChordDefinitionStructure struct) {
        Voicing seventhVoicing = new VoicingImpl(VoicingValidator.SEVENTH_VALIDATOR);
        seventhVoicing.addChordMember(ChordMember.ROOT);
        seventhVoicing.addChordMember(ChordMember.FIFTH);
        seventhVoicing.addChordMember(ChordMember.SEVENTH);
        seventhVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder builder = new VoicedChordBuilder(
            struct.getChordSpec("Seventh", Quality.DOMINANT), 
            seventhVoicing, 
            defaultOctaveShift, 
            Duration.QUARTER,
            VoicePart.barbershopDefault());
        
        return builder;
    }

    public static VoicedChordBuilder getTriadBuilder(ChordDefinitionStructure struct) {
        Voicing triadVoicing = new VoicingImpl(VoicingValidator.TRIAD_VALIDATOR);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.FIFTH);
        triadVoicing.addChordMember(ChordMember.ROOT);
        triadVoicing.addChordMember(ChordMember.THIRD);
        
        VoicedChordBuilder triadBuilder = new VoicedChordBuilder(
            struct.getChordSpec("Triad", Quality.MAJOR), 
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