package music.chord.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingComparison;
import music.chord.arrangement.VoicingStrategy;

public class ClosestVoicingStrategy implements VoicingStrategy {

    private DerivedChordBuilder chordBuilder;


    public ClosestVoicingStrategy(DerivedChordBuilder chordBuilder) {
        this.chordBuilder = chordBuilder;
    }
    
    
    @Override
    public VoicedChord voice(
            VoicedChord previousChord, 
            VoicedChord nextChord, 
            List<Voicing> voicingList) {
        
        VoicedChord result = null;
        
        for(Voicing currentVoicing : voicingList) {
            VoicedChord currentVoicedChord = chordBuilder.setChord(nextChord)
                .setVoicing(currentVoicing)
                .buildVoicedChord();
            
            if(
                result == null 
                || currentVoicedChord.difference(previousChord) 
                    < result.difference(previousChord)
            ) {
                result = currentVoicedChord;
            }
        }
        
        return result;
    }
    
    
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord,
            List<Voicing> voicingList) {
        
        List<VoicingComparison> result = new ArrayList<VoicingComparison>();
        
        for(Voicing voicing : voicingList) {
            VoicedChord chord = chordBuilder.setChord(newChord)
                .setVoicing(voicing)
                .buildVoicedChord();
            Integer difference = chord.difference(previousChord);           
            result.add(new VoicingComparison(difference, voicing));
        }
        
        Collections.sort(result);
        
        return result;
    }
}
