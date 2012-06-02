package music.chord.command;

import java.util.List;

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.VoicedChord;
import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public class FindChordsByChordMember implements Command {

    private NoteName note;
    private ChordMember member;
    private ChordFinder finder;

    public FindChordsByChordMember(NoteName note, ChordMember member, ChordFinder finder) {
        this.note = note;
        this.member = member;
        this.finder = finder;
    }
    
    @Override
    public void execute() {
        List<VoicedChord> chordList = finder.find(note, member);
        
        for(VoicedChord chord : chordList) {
            System.out.println(
                    chord.noteNameFromChordMember(ChordMember.ROOT) + " " + chord.getSymbol());
        }
    }

}
