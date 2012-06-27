package music.chord.arrangement;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.grammar.ChordDefinitionStructureFactory;

import org.junit.Before;
import org.junit.Test;

public class ChordFinderTest {
    private ChordDefinitionStructure struct;
    private ChordFinder finder;
    
    @Before
    public void setUp() throws Exception {
        struct = ChordDefinitionStructureFactory.getInstance(Constants.getChordDefinitions());   
        finder = new ChordFinderImpl(
                BuilderFactory.getTriadBuilder(struct),
                BuilderFactory.getSeventhBuilder(struct),
                BuilderFactory.getNinthBuilder(struct),
                struct);
    }

    @Test
    public void findByChordMember() {
        List<VoicedChord> chordList = finder.find(NoteName.forSymbol("Bb"), ChordMember.NINTH);        
        assertTrue("Found " + chordList.size() + " chords.", chordList.size() > 0);
    }
    
    @Test
    public void findByNoteList() {
        List<NoteName> noteList = new ArrayList<NoteName>();
        noteList.add(NoteName.forSymbol("F#"));
        List<VoicedChord> chordList = finder.find(noteList);        
        assertTrue("Found " + chordList.size() + " chords.", chordList.size() > 0);
    }

}
