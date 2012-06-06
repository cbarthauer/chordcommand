package music.chord.display;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordMember;
import music.chord.base.Interval;
import music.chord.base.IntervalDirective;
import music.chord.base.NoteName;
import music.chord.base.Quality;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileFormatterTest {

    private static HashMap<ChordMember, IntervalDirective> dirMap;
    private static ChordDefinitionStructure struct;
    private static VoicedChordBuilder triadBuilder;

    @BeforeClass
    public static void setUp() throws Exception {
        dirMap = new HashMap<ChordMember, IntervalDirective>();
        dirMap.put(ChordMember.THIRD, new IntervalDirective(Interval.MAJOR_THIRD));
        dirMap.put(ChordMember.FIFTH, new IntervalDirective(Interval.PERFECT_FIFTH));
        
        struct = new ChordDefinitionStructure();
        struct.addQuality("Triad", Quality.MAJOR_TRIAD, dirMap);
        
        triadBuilder = BuilderFactory.getTriadBuilder(struct);
    }

    @Test
    public void testFormat() {
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(majorTriad(NoteName.forSymbol("C")));
        chordList.add(majorTriad(NoteName.forSymbol("F")));
        chordList.add(majorTriad(NoteName.forSymbol("G")));
        chordList.add(majorTriad(NoteName.forSymbol("C")));
        
        ChordListFormatter formatter = new FileFormatter();
        String result = formatter.format(chordList);
        System.out.print(result);
        
        assertTrue(result.length() > 0);
    }

    private VoicedChord majorTriad(NoteName root) {
        return triadBuilder.setRoot(root)
                .setChordSpec(struct.getChordSpec("Triad", Quality.MAJOR_TRIAD))
                .setQuality(Quality.MAJOR_TRIAD)
                .buildVoicedChord();
    }

}
