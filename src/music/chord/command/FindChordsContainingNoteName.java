package music.chord.command;

import java.util.List;

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.VoicedChord;
import music.chord.base.NoteName;

public class FindChordsContainingNoteName implements Command {

    private NoteName note;
    private ChordFinder finder;

    public FindChordsContainingNoteName(NoteName note, ChordFinder finder) {
        this.note = note;
        this.finder = finder;
    }
    
    @Override
    public void execute() {
        List<VoicedChord> chordList = finder.find(note);
        
        for(VoicedChord chord : chordList) {
            System.out.println(chord.getSymbol());
        }
    }

}
