package music.chord.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum VoicePart {
    BASS,
    BARITONE,
    LEAD,
    TENOR;
    
    private static final List<VoicePart> BARBERSHOP;
    private static final Map<String, VoicePart> NAME_MAP;
    
    static {
        BARBERSHOP = new ArrayList<VoicePart>();
        BARBERSHOP.add(VoicePart.BASS);
        BARBERSHOP.add(VoicePart.BARITONE);
        BARBERSHOP.add(VoicePart.LEAD);
        BARBERSHOP.add(VoicePart.TENOR);
        
        NAME_MAP = new HashMap<String, VoicePart>();
        for(VoicePart part : VoicePart.values()) {
            NAME_MAP.put(part.name(), part);
        }
    }
    
    public static List<VoicePart> barbershopDefault() {
        return new ArrayList<VoicePart>(BARBERSHOP);
    }
    
    public static VoicePart voicePartFromName(String name) {
        return NAME_MAP.get(name.toUpperCase());
    }
}
