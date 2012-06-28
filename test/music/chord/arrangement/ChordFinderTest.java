package music.chord.arrangement;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;

import org.junit.Before;
import org.junit.Test;

public class ChordFinderTest {

    private ChordFinder finder;

    @Before
    public void setUp() throws Exception {
        QualityRegistry qualities = QualityRegistryFactory.getInstance(
                Constants.getChordDefinitions());
        
        finder = new ChordFinderImpl(
                BuilderFactory.getTriadBuilder(
                    NoteName.forSymbol("C"),
                    qualities.forName("MAJOR_TRIAD")),
                BuilderFactory.getSeventhBuilder(
                    NoteName.forSymbol("C"),
                    qualities.forName("DOMINANT_SEVENTH")),
                BuilderFactory.getNinthBuilder(
                    NoteName.forSymbol("C"),
                    qualities.forName("DOMINANT_NINTH")),
                qualities.all());
    }

    @Test
    public void findByNoteList() {
        List<NoteName> noteList = new ArrayList<NoteName>();
        noteList.add(NoteName.forSymbol("C"));
        noteList.add(NoteName.forSymbol("E"));
        noteList.add(NoteName.forSymbol("G"));
        noteList.add(NoteName.forSymbol("Bb"));
        
        List<VoicedChord> chordList = finder.find(noteList);
        assertTrue(chordList.size() > 0);
    }
    
    @Test
    public void findByChordMember() {
        List<VoicedChord> chordList = finder.find(NoteName.forSymbol("C"), ChordMember.ROOT);
        assertTrue(chordList.size() > 11);
    }

}
