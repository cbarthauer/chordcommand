package music.chord.arrangement;

import static org.junit.Assert.assertEquals;
import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;

import org.junit.Before;
import org.junit.Test;

public class VoicedChordBuilderTest {

    private QualityRegistry qualities;
    private VoicedChordBuilder chordBuilder;

    @Before
    public void setUp() throws Exception {
        qualities = QualityRegistryFactory.getInstance(Constants.getChordDefinitions());
        chordBuilder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"), 
                qualities.forName("MAJOR_TRIAD"));
    }

    @Test
    public void buildVoicedChord() {
        VoicedChord chord = chordBuilder.buildVoicedChord();
        assertEquals(NoteName.forSymbol("C"), chord.noteNameFromChordMember(ChordMember.ROOT));
    }

}
