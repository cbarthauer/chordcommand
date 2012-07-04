package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public final class IntervallicStructureBuilder {

    private Map<ChordMember, IntervalDirective> dirMap;
    
    public IntervallicStructureBuilder() {
        dirMap = new HashMap<ChordMember, IntervalDirective>();
    }
    
    public final IntervallicStructureBuilder add(
            ChordMember member, 
            IntervalDirective directive) {
        
        dirMap.put(member, directive);
        return this;
    }

    public final IntervallicStructure build() {
        IntervallicStructure struct = new IntervallicStructure(dirMap);
        dirMap = new HashMap<ChordMember, IntervalDirective>();
        return struct;
    }

}
