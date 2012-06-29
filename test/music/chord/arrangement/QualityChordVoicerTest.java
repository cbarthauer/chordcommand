package music.chord.arrangement;

import static org.junit.Assert.assertTrue;

import java.util.List;

import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;

import org.junit.Before;
import org.junit.Test;

public class QualityChordVoicerTest {

    private ChordVoicer voicer;
    private VoicedChord previousChord;
    private VoicedChord newChord;

    @Before
    public void setUp() throws Exception {
        QualityRegistry qualities = QualityRegistryFactory.getInstance(
            Constants.getChordDefinitions());
        
        voicer = new QualityChordVoicer(
                new ClosestVoicingStrategy(
                        new DerivedChordBuilder()),
                qualities);
        
        VoicedChordBuilder builder = BuilderFactory.getTriadBuilder(
            NoteName.forSymbol("C"),
            qualities.forName("MAJOR_TRIAD"));
        
        previousChord = builder.buildVoicedChord();
        newChord = builder.setRoot(NoteName.forSymbol("G"))
                .buildVoicedChord();
    }

    @Test
    public void voicingComparisonList() {
        List<VoicingComparison> compList = voicer.voicingComparisonList(
                previousChord, 
                newChord);
        assertTrue(compList.size() > 0);
    }

}
