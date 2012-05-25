package music.chord.decorator;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public class ChordImpl implements Chord {

    private NoteName root;
    private ChordSpec spec;

    public ChordImpl(NoteName root, ChordSpec spec) {
        this.root = root;
        this.spec = spec;
    }
    
    @Override
    public NoteName noteNameFromChordMember(ChordMember chordMember) {
        return spec.getNoteName(root, chordMember);
    }

}
