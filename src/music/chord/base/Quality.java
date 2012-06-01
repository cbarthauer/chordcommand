package music.chord.base;

import java.util.HashMap;
import java.util.Map;

public enum Quality {
    AUGMENTED,
    DIMINISHED,
    MAJOR,
    MINOR,
    DOMINANT,
    HALF_DIMINISHED,
    MINOR_MAJOR,
    SUSPENDED;
    
    private static final Map<String, Quality> nameMap;
    
    static {
        nameMap = new HashMap<String, Quality>();
        
        for(Quality quality : Quality.values()) {
            nameMap.put(quality.name(), quality);
        }
    }
    
    public static Quality forName(String name) {
        return nameMap.get(name.toUpperCase());
    }
}
