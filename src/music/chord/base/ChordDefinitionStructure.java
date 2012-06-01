package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.Voicing;

public final class ChordDefinitionStructure {
    private Map<String, Map<Quality, ChordSpec>> chordMap;
    private Map<String, List<Voicing>> voicingMap;
    private List<Voicing> allVoicings;
    
    public ChordDefinitionStructure() {
        chordMap = new HashMap<String, Map<Quality, ChordSpec>>();
        voicingMap = new HashMap<String, List<Voicing>>();
        allVoicings = new ArrayList<Voicing>();
    }
    
    public final void addQuality(
            String type, 
            Quality quality, 
            Map<ChordMember, IntervalDirective> dirMap) {
        
        Map<Quality, ChordSpec> qualityMap = chordMap.get(type);
        
        if(qualityMap == null) {
            qualityMap = new HashMap<Quality, ChordSpec>();
        }
        
        qualityMap.put(quality, new ChordSpec(dirMap));
        
        chordMap.put(type, qualityMap);
    }
    
    public final void addVoicing(String type, Voicing voicing) {
        List<Voicing> voicingList = voicingMap.get(type);
        
        if(voicingList == null) {
            voicingList = new ArrayList<Voicing>();
        }
        
        voicingList.add(voicing);
        voicingMap.put(type, voicingList);
        
        allVoicings.add(voicing);
    }

    public final ChordSpec getChordSpec(String type, Quality quality) {
        return chordMap.get(type).get(quality);
    }

    public final List<Voicing> getVoicings(String type) {
        return voicingMap.get(type);
    }
    
    public final String toString() {
        return chordMap.toString() + "\n" + voicingMap;
    }

    public final List<Voicing> getCongruentVoicings(Voicing voicing) {
        List<Voicing> result = new ArrayList<Voicing>();
        
        for(Voicing currentVoicing : allVoicings) {
            if(currentVoicing.isCongruentTo(voicing)) {
                result.add(currentVoicing);
            }
        }
        
        return result;
    }
}
