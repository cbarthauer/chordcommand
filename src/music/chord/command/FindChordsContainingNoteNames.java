package music.chord.command;

import java.util.List;

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.VoicedChord;
import music.chord.base.NoteName;

public class FindChordsContainingNoteNames implements Command {

    private List<NoteName> noteNameList;
    private ChordFinder finder;

    public FindChordsContainingNoteNames(List<NoteName> noteNameList, ChordFinder finder) {
        this.noteNameList = noteNameList;
        this.finder = finder;
    }
    
    @Override
    public void execute() {
        List<VoicedChord> chordList = finder.find(noteNameList);
        
        for(VoicedChord chord : chordList) {
            System.out.println(chord.getSymbol());
        }
    }

}
