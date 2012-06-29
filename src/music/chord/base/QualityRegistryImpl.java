package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.Voicing;

public final class QualityRegistryImpl implements QualityRegistry {

    private Map<String, Quality> nameMap;
    private Map<ChordType, List<Quality>> typeMap;
    private Map<ChordType, List<Voicing>> voicingMap;
    
    public QualityRegistryImpl() {
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

    /* (non-Javadoc)
     * @see music.chord.base.QualityRegistry#all()
     */
    @Override
    public final List<Quality> all() {
        return new ArrayList<Quality>(nameMap.values());
    }

    /* (non-Javadoc)
     * @see music.chord.base.QualityRegistry#all(music.chord.base.ChordType)
     */
    @Override
    public final List<Quality> all(ChordType type) {
        return typeMap.get(type);
    }

    /* (non-Javadoc)
     * @see music.chord.base.QualityRegistry#contains(music.chord.base.Quality)
     */
    @Override
    public final boolean contains(Quality quality) {
        return nameMap.containsValue(quality);
    }

    /* (non-Javadoc)
     * @see music.chord.base.QualityRegistry#forName(java.lang.String)
     */
    @Override
    public final Quality forName(String name) {
        return nameMap.get(name);
    }

    @Override
    public final List<Voicing> forQuality(Quality quality) {
        return voicingMap.get(quality.getType());
    }

    public final String toString() {
        return 
            this.nameMap.toString()
            + "\n" + this.voicingMap;
    }
}
