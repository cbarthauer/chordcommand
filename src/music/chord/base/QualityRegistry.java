package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.Voicing;

public final class QualityRegistry {

    private Map<String, Quality> nameMap;
    private Map<ChordType, List<Quality>> typeMap;
    private Map<ChordType, List<Voicing>> voicingMap;
    
    public QualityRegistry() {
        this.nameMap = new HashMap<String, Quality>();
        this.typeMap = new HashMap<ChordType, List<Quality>>();
        this.voicingMap = new HashMap<ChordType, List<Voicing>>();
    }
    
    public final void addQuality(Quality quality) {
        nameMap.put(quality.getName(), quality);
        
        List<Quality> qualityList = typeMap.get(quality.getType());
        
        if(qualityList == null) {
            qualityList = new ArrayList<Quality>();
        }
        
        qualityList.add(quality);
        
        typeMap.put(quality.getType(), qualityList);
    }
    
    public final void addVoicings(ChordType type, List<Voicing> voicings) {
        voicingMap.put(type, new ArrayList<Voicing>(voicings));
    }

    public final List<Quality> all() {
        return new ArrayList<Quality>(nameMap.values());
    }

    public final List<Quality> all(ChordType type) {
        return typeMap.get(type);
    }

    public final boolean contains(Quality quality) {
        return nameMap.containsValue(quality);
    }

    public final Quality forName(String name) {
        return nameMap.get(name);
    }

    public final String toString() {
        return 
            this.nameMap.toString()
            + "\n" + this.voicingMap;
    }
}
