package music.chord.arrangement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.base.ChordType;
import music.chord.base.Quality;

public final class QualityRegistryImpl implements QualityRegistry {

    private Map<String, Quality> nameMap;
    private Map<ChordType, List<Quality>> typeMap;
    private Map<ChordType, List<Voicing>> voicingMap;
    private List<Voicing> voicingList;
    
    public QualityRegistryImpl() {
        this.nameMap = new HashMap<String, Quality>();
        this.typeMap = new HashMap<ChordType, List<Quality>>();
        this.voicingMap = new HashMap<ChordType, List<Voicing>>();
        this.voicingList = new ArrayList<Voicing>();
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
        voicingList.addAll(voicings);
    }

    @Override
    public final List<Quality> all() {
        return new ArrayList<Quality>(nameMap.values());
    }

    @Override
    public final List<Quality> all(ChordType type) {
        return typeMap.get(type);
    }

    @Override
    public final boolean contains(Quality quality) {
        return nameMap.containsValue(quality);
    }

    @Override
    public final Quality forName(String name) {
        return nameMap.get(name);
    }

    @Override
    public final List<Voicing> forQuality(Quality quality) {
        return voicingMap.get(quality.getType());
    }

    @Override
    public final List<Voicing> getCongruentVoicings(Voicing voicing) {
        List<Voicing> result = new ArrayList<Voicing>();
        
        for(Voicing currentVoicing : voicingList) {
            if(currentVoicing.isCongruentTo(voicing)) {
                result.add(currentVoicing);
            }
        }
        
        return result;
    }

    public final String toString() {
        return 
            this.nameMap.toString()
            + "\n" + this.voicingMap;
    }
}
