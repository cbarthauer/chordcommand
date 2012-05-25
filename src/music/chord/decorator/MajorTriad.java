package music.chord.decorator;

import music.chord.base.ChordMember;
import music.chord.base.Interval;
import music.chord.base.NoteName;

public class MajorTriad implements Chord {

    private NoteName root;
    
    public MajorTriad(NoteName root) {
        this.root = root;
    }
    
    @Override
    public NoteName noteNameFromChordMember(ChordMember chordMember) {
        NoteName result = null;
        
        switch(chordMember) {
            case ROOT:
                result = root;
                break;
            case THIRD:
                result = root.up(Interval.MAJOR_THIRD);
                break;
            case FIFTH:
                result = root.up(Interval.PERFECT_FIFTH);
                break;
            default:
                throw new IllegalArgumentException("Invalid chordMember: " + chordMember);    
        }
        
        return result;
    }

}
