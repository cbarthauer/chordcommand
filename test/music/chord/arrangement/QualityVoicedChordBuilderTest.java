package music.chord.arrangement;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import music.chord.base.ChordMember;
import music.chord.base.ChordType;
import music.chord.base.Duration;
import music.chord.base.Interval;
import music.chord.base.IntervalDirective;
import music.chord.base.IntervallicStructureBuilder;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.base.QualityRegistryFactory;
import music.chord.base.QualitySymbol;
import music.chord.base.VoicePart;

import org.junit.Before;
import org.junit.Test;

public class QualityVoicedChordBuilderTest {

    private QualityVoicedChordBuilder chordBuilder;
    private IntervallicStructureBuilder structBuilder;

    @Before
    public void setUp() throws Exception {
        structBuilder = new IntervallicStructureBuilder();
        
        Quality quality = QualityRegistryFactory.createQuality(
            "MAJOR_TRIAD", 
            QualitySymbol.MAJOR_TRIAD, 
            ChordType.TRIAD, 
            structBuilder
                .add(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD))
                .add(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH))
                .build());
        
        VoicedChordConfig config = new VoicedChordConfig(
            NoteName.forSymbol("C"),
            quality,
            VoicingFactory.instanceFromChordMemberList(
                ChordMember.ROOT, 
                ChordMember.FIFTH, 
                ChordMember.ROOT, 
                ChordMember.THIRD),
            4,
            Duration.QUARTER,
            new ArrayList<VoicePart>());
        chordBuilder = new QualityVoicedChordBuilder(config);
    }

    @Test
    public void buildVoicedChord() {
        VoicedChord chord = chordBuilder.buildVoicedChord();
        assertEquals(NoteName.forSymbol("C"), chord.noteNameFromChordMember(ChordMember.ROOT));
    }

}
