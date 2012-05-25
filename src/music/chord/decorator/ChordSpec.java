package music.chord.decorator;

import java.util.HashMap;
import java.util.Map;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public class ChordSpec {

    private Map<ChordMember, IntervalDirective> intervalDirectiveMap;
    
    public ChordSpec(Map<ChordMember, IntervalDirective> intervalDirectiveMap) {
        this.intervalDirectiveMap = 
                new HashMap<ChordMember, IntervalDirective>(intervalDirectiveMap);
    }
    
    public NoteName getNoteName(NoteName root, ChordMember chordMember) {
        IntervalDirective directive = intervalDirectiveMap.get(chordMember);
        NoteName result = directive.calculate(root);
        return result;
    }

}
