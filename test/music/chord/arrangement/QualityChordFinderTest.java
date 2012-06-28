package music.chord.arrangement;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.base.ChordMember;
import music.chord.base.ChordType;
import music.chord.base.Constants;
import music.chord.base.Interval;
import music.chord.base.IntervalDirective;
import music.chord.base.IntervallicStructureBuilder;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistryFactory;
import music.chord.base.QualitySymbol;

import org.junit.Before;
import org.junit.Test;

public class QualityChordFinderTest {

    private ChordFinder finder;

    @Before
    public void setUp() throws Exception {
        IntervallicStructureBuilder struct = new IntervallicStructureBuilder();
        
        finder = new QualityChordFinder(
                BuilderFactory.getTriadBuilder(
                    NoteName.forSymbol("C"),
                    QualityRegistryFactory.createQuality(
                        "Triad", 
                        QualitySymbol.MAJOR_TRIAD, 
                        ChordType.TRIAD, 
                        struct.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                            .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                            .build())),
                BuilderFactory.getSeventhBuilder(
                    NoteName.forSymbol("C"),
                    QualityRegistryFactory.createQuality(
                        "Seventh", 
                        QualitySymbol.DOMINANT_SEVENTH, 
                        ChordType.SEVENTH, 
                        struct.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                            .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                            .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                            .build())),
                BuilderFactory.getNinthBuilder(
                    NoteName.forSymbol("C"),
                    QualityRegistryFactory.createQuality(
                        "Ninth", 
                        QualitySymbol.DOMINANT_NINTH, 
                        ChordType.NINTH, 
                        struct.add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                            .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                            .add(ChordMember.SEVENTH, new IntervalDirective(Interval.MINOR_SEVENTH))
                            .add(ChordMember.NINTH, new IntervalDirective(Interval.MAJOR_NINTH))
                            .build())),
                QualityRegistryFactory.getInstance(Constants.getChordDefinitions()));
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
