package music.chord.base;

import java.util.HashMap;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public class IntervallicStructure {

    private Map<ChordMember, IntervalDirective> intervalDirectiveMap;
    
    IntervallicStructure(Map<ChordMember, IntervalDirective> intervalDirectiveMap) {
        this.intervalDirectiveMap = 
                new HashMap<ChordMember, IntervalDirective>(intervalDirectiveMap);
    }
    
    public NoteName getNoteName(NoteName root, ChordMember chordMember) {
        IntervalDirective directive = intervalDirectiveMap.get(chordMember);
        NoteName result = null;
        
        if(directive != null) {
            result = directive.calculate(root);
        }
        
        return result;
    }

}
