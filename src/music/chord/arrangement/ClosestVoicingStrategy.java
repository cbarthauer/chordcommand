package music.chord.arrangement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.builder.DerivedChordBuilder;

public class ClosestVoicingStrategy implements VoicingStrategy {

    private List<Voicing> triadVoicingList;
    private List<Voicing> seventhVoicingList;
    private DerivedChordBuilder chordBuilder;


    public ClosestVoicingStrategy(          
            List<Voicing> triadVoicingList, 
            List<Voicing> seventhVoicingList, 
            DerivedChordBuilder chordBuilder) {
        
        this.triadVoicingList = triadVoicingList;
        this.seventhVoicingList = seventhVoicingList;
        this.chordBuilder = chordBuilder;
    }
    
    
    @Override
    public VoicedChord voice(VoicedChord previousChord, VoicedChord nextChord) {
        List<Voicing> voicingList = voicingListFromVoicing(nextChord.getVoicing());
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
    
    
    public List<Voicing> voicingListFromVoicing(Voicing voicing) {
        if(voicing instanceof SeventhVoicing) {
            return seventhVoicingList;
        }
        else {
            return triadVoicingList;
        }
    }

    
    public Map<Integer, List<VoicedChord>> voicedChordDifferenceMap(
            VoicedChord previousChord, 
            VoicedChord newChord) {
        
        List<Voicing> voicingList = voicingListFromVoicing(newChord.getVoicing());
        Map<Integer, List<VoicedChord>> diffMap = new HashMap<Integer, List<VoicedChord>>();
        
        for(Voicing voicing : voicingList) {
            VoicedChord chord = chordBuilder.setChord(newChord)
                .setVoicing(voicing)
                .buildVoicedChord();
            Integer difference = chord.difference(previousChord);
            
            List<VoicedChord> chordList = 
                diffMap.get(difference) == null 
                ? new ArrayList<VoicedChord>() 
                : diffMap.get(difference);
            
            chordList.add(chord);
            
            diffMap.put(difference, chordList);
        }
        
        return diffMap;
    }
    
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord) {
        
        List<Voicing> voicingList = voicingListFromVoicing(newChord.getVoicing());
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
