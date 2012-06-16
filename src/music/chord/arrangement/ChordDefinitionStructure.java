package music.chord.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.ChordSpec;
import music.chord.base.ChordType;
import music.chord.base.IntervalDirective;
import music.chord.base.Quality;

public final class ChordDefinitionStructure {
    private Map<ChordType, Map<Quality, ChordSpec>> chordMap;
    private Map<ChordType, List<Voicing>> voicingMap;
    private List<Voicing> allVoicings;
    
    public ChordDefinitionStructure() {
        chordMap = new HashMap<ChordType, Map<Quality, ChordSpec>>();
        voicingMap = new HashMap<ChordType, List<Voicing>>();
        allVoicings = new ArrayList<Voicing>();
    }
    
    public final void addQuality(
            ChordType type, 
            Quality quality, 
            Map<ChordMember, IntervalDirective> dirMap) {
        
        Map<Quality, ChordSpec> qualityMap = chordMap.get(type);
        
        if(qualityMap == null) {
            qualityMap = new HashMap<Quality, ChordSpec>();
        }
        
        qualityMap.put(quality, new ChordSpec(dirMap));
        
        chordMap.put(type, qualityMap);
    }
    
    public final void addVoicing(ChordType type, Voicing voicing) {
        List<Voicing> voicingList = voicingMap.get(type);
        
        if(voicingList == null) {
            voicingList = new ArrayList<Voicing>();
        }
        
        voicingList.add(voicing);
        voicingMap.put(type, voicingList);
        
        allVoicings.add(voicing);
    }

    public final ChordSpec getChordSpec(ChordType type, Quality quality) {
        return chordMap.get(type).get(quality);
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
    
    public final List<Voicing> getVoicings(String type) {
        return voicingMap.get(type);
    }

    public final Map<Quality, ChordSpec> qualityMapFromType(ChordType type) {
        return new HashMap<Quality, ChordSpec>(chordMap.get(type));
    }

    public final String toString() {
        return chordMap.toString() + "\n" + voicingMap;
    }
}