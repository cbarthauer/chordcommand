package music.chord.engine.protocol.filter;

import music.chord.arrangement.VoicedChord;
import music.chord.base.ChordMember;
import music.chord.base.NoteName;
import music.chord.engine.protocol.filter.ChordMemberFilter;

public final class EqualsFilter implements ChordMemberFilter {

    private ChordMember member;
    private NoteName noteName;

    public EqualsFilter(ChordMember member, NoteName noteName) {
        this.member = member;
        this.noteName = noteName;
    }

    @Override
    public final boolean filter(VoicedChord chord) {
        NoteName current = chord.noteNameFromChordMember(member);
        return noteName.equals(current);
    }

}
