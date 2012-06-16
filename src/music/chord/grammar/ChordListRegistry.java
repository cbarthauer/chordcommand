package music.chord.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import music.chord.arrangement.VoicedChord;
import music.chord.engine.protocol.Identifier;

public final class ChordListRegistry {

    private Map<Identifier, List<VoicedChord>> data;
    
    public ChordListRegistry() {
        data = new HashMap<Identifier, List<VoicedChord>>();
    }
    
    public final void add(Identifier identifier, VoicedChord chord) {
        List<VoicedChord> chordList = data.get(identifier);
        
        if(chordList == null) {
            chordList = new ArrayList<VoicedChord>();
        }

        chordList.add(chord);
        data.put(identifier, chordList);
    }
    
    public final List<VoicedChord> byIdentifier(Identifier identifier) {
        List<VoicedChord> result = data.get(identifier);
        
        if(result == null) {
            return new ArrayList<VoicedChord>();
        }
        else {
            return new ArrayList<VoicedChord>(result);
        }
    }

    public final VoicedChord getChord(Identifier identifier, int index) {
        return data.get(identifier).get(index);
    }
    
    public final void put(Identifier identifier, List<VoicedChord> chordList) {
        data.put(identifier, new ArrayList<VoicedChord>(chordList));
    }

    public final void set(Identifier identifier, int index, VoicedChord chord) {
        data.get(identifier).set(index, chord);
    }

}
