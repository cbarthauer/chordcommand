package music.chord.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.VoicedChord;

public final class ChordListRegistry {

    private Map<String, List<VoicedChord>> data;
    
    public ChordListRegistry() {
        data = new HashMap<String, List<VoicedChord>>();
    }
    
    public final void add(String identifier, VoicedChord chord) {
        List<VoicedChord> chordList = data.get(identifier);
        
        if(chordList == null) {
            chordList = new ArrayList<VoicedChord>();
        }

        chordList.add(chord);
        data.put(identifier, chordList);
    }

    public final List<VoicedChord> byIdentifier(String identifier) {
        List<VoicedChord> result = data.get(identifier);
        
        if(result == null) {
            return null;
        }
        else {
            return new ArrayList<VoicedChord>(result);
        }
    }

    public final List<VoicedChord> byIdentifier(String identifier, boolean create) {
        List<VoicedChord> result = byIdentifier(identifier);
        
        if(create && result == null) {
            put(identifier, new ArrayList<VoicedChord>());
            result = byIdentifier(identifier);
        }
        
        return result;
    }

    public final VoicedChord getChord(String identifier, int index) {
        return data.get(identifier).get(index);
    }
    
    public final void put(String identifier, List<VoicedChord> chordList) {
        data.put(identifier, new ArrayList<VoicedChord>(chordList));
    }

    public final void set(String identifier, int index, VoicedChord chord) {
        data.get(identifier).set(index, chord);
    }

}
