package music.chord.arrangement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import music.chord.builder.DerivedChordBuilder;

public class ClosestVoicingStrategy implements VoicingStrategy {

    private VoicingManager manager;
    private DerivedChordBuilder chordBuilder;


    public ClosestVoicingStrategy(VoicingManager manager, DerivedChordBuilder chordBuilder) {
        this.manager = manager;
        this.chordBuilder = chordBuilder;
    }
    
    
    @Override
    public VoicedChord voice(VoicedChord previousChord, VoicedChord nextChord) {
        List<Voicing> voicingList = manager.voicingListFromVoicing(nextChord.getVoicing());
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
    
    
//    public Map<Integer, List<VoicedChord>> voicedChordDifferenceMap(
//            VoicedChord previousChord, 
//            VoicedChord newChord) {
//        
//        List<Voicing> voicingList = manager.voicingListFromVoicing(newChord.getVoicing());
//        Map<Integer, List<VoicedChord>> diffMap = new HashMap<Integer, List<VoicedChord>>();
//        
//        for(Voicing voicing : voicingList) {
//            VoicedChord chord = chordBuilder.setChord(newChord)
//                .setVoicing(voicing)
//                .buildVoicedChord();
//            Integer difference = chord.difference(previousChord);
//            
//            List<VoicedChord> chordList = 
//                diffMap.get(difference) == null 
//                ? new ArrayList<VoicedChord>() 
//                : diffMap.get(difference);
//            
//            chordList.add(chord);
//            
//            diffMap.put(difference, chordList);
//        }
//        
//        return diffMap;
//    }
    
    public List<VoicingComparison> voicingComparisonList(
            VoicedChord previousChord, 
            VoicedChord newChord) {
        
        List<Voicing> voicingList = manager.voicingListFromVoicing(newChord.getVoicing());
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
