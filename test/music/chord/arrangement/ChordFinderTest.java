package music.chord.arrangement;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.NoteName;
import music.chord.grammar.ChordDefinitionStructureFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ChordFinderTest {
    private ChordDefinitionStructure struct;
    private ChordFinder finder;
    
    @Before
    public void setUp() throws Exception {
        struct = ChordDefinitionStructureFactory.getInstance(
                "D:\\musicspace\\chordgrammar\\definitions\\chords.txt");   
        finder = new ChordFinder(struct);
    }

    @Test
    @Ignore
    public void testFindByChordMember() {
        List<VoicedChord> chordList = finder.find(NoteName.forSymbol("Bb"), ChordMember.NINTH);
        
        for(VoicedChord chord : chordList) {
            System.out.println(
                    chord.noteNameFromChordMember(ChordMember.ROOT) + " " + chord.getSymbol());
        }
        
        assertTrue("Found " + chordList.size() + " chords.", chordList.size() > 0);
    }
    
    @Test
    public void testFindByNoteList() {
        List<NoteName> noteList = new ArrayList<NoteName>();
        noteList.add(NoteName.forSymbol("F#"));
        List<VoicedChord> chordList = finder.find(noteList);
        
        for(VoicedChord chord : chordList) {
            System.out.println(chord.getSymbol());
        }
        
        assertTrue("Found " + chordList.size() + " chords.", chordList.size() > 0);
    }

}
