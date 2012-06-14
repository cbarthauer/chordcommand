package music.chord.grammar;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.NoteName;
import music.chord.base.Quality;

import org.junit.Before;
import org.junit.Test;

public class ChordListRegistryTest {

    private ChordListRegistry reg;
    private ChordDefinitionStructure struct;
    private VoicedChordBuilder builder;
    
    @Before
    public void setUp() throws Exception {
        reg = new ChordListRegistry();
        
        struct = ChordDefinitionStructureFactory.getInstance("definitions/chords.txt");
        builder = BuilderFactory.getTriadBuilder(struct);
    }

    @Test
    public void testAdd() {
        VoicedChord chord1 = getChord("C", "Triad", Quality.MAJOR_TRIAD);
        reg.add("c1", chord1);
        reg.add("c1", chord1);
        
        List<VoicedChord> chordList = reg.byIdentifier("c1");
        assertTrue(chordList.size() == 2);
    }
    
    @Test
    public void testByIdentifier() {
        VoicedChord chord1 = getChord("C", "Triad", Quality.MAJOR_TRIAD);
            
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(chord1);
        
        reg.put("c1", chordList);
        
        List<VoicedChord> retrievedList = reg.byIdentifier("c1");
        assertTrue(retrievedList.size() == 1);
        
        retrievedList = reg.byIdentifier("does not exist");
        assertTrue(retrievedList == null);
        
        retrievedList = reg.byIdentifier("does not exist, but create one for me", true);
        assertTrue(retrievedList.size() == 0);
        
        retrievedList = reg.byIdentifier("does not exist, and don't create one for me", false);
        assertTrue(retrievedList == null);
    }

    @Test
    public void testGetChord() {
        VoicedChord chord1 = getChord("C", "Triad", Quality.MAJOR_TRIAD);
        reg.add("c1", chord1);
        
        VoicedChord fromReg = reg.getChord("c1", 0);
        assertTrue(fromReg.getSymbol().equals(chord1.getSymbol()));
    }
    
    @Test
    public void testSet() {
        VoicedChord chord1 = getChord("C", "Triad", Quality.MAJOR_TRIAD);
        reg.add("c1", chord1);
        
        VoicedChord chord2 = getChord("D", "Seventh", Quality.DIMINISHED_SEVENTH);
        reg.set("c1", 0, chord2);
        
        assertTrue(reg.byIdentifier("c1").get(0).getSymbol().equals(chord2.getSymbol()));
    }    

    private VoicedChord getChord(String root, String type, Quality quality) {
        return builder.setRoot(NoteName.forSymbol(root))
                .setChordSpec(struct.getChordSpec(type, quality))
                .setQuality(quality)
                .buildVoicedChord();
    }

}
