package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public enum ChordType {
    TRIAD, SEVENTH, NINTH;
    
    private static Map<String, ChordType> typeMap;
    
    static {
        typeMap = new HashMap<String, ChordType>();
        
        for(ChordType type : ChordType.values()) {
            typeMap.put(type.name(), type);
        }
    }
    
    public static ChordType forName(String name) {
        return typeMap.get(name.toUpperCase());
    }
}
