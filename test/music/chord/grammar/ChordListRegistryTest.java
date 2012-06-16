package music.chord.grammar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.Identifier;

import org.junit.Before;
import org.junit.Test;

public class ChordListRegistryTest {

    private ChordListRegistry reg;
    private ChordDefinitionStructure struct;
    private VoicedChordBuilder builder;
    private Identifier c1;
    
    @Before
    public void setUp() throws Exception {
        reg = new ChordListRegistry();
        
        struct = ChordDefinitionStructureFactory.getInstance("definitions/chords.txt");
        builder = BuilderFactory.getTriadBuilder(struct);
        c1 = new Identifier("c1");
    }

    @Test
    public void add() {
        VoicedChord chord1 = getChord("C", ChordType.TRIAD, Quality.MAJOR_TRIAD);
        reg.add(c1, chord1);
        reg.add(c1, chord1);
        
        List<VoicedChord> chordList = reg.byIdentifier(c1);
        assertTrue(chordList.size() == 2);
    }
    
    @Test
    public void byIdentifier() {
        VoicedChord chord1 = getChord("C", ChordType.TRIAD, Quality.MAJOR_TRIAD);
            
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(chord1);
        
        reg.put(c1, chordList);
        
        List<VoicedChord> retrievedList = reg.byIdentifier(c1);
        assertEquals(retrievedList.size(), 1);
        assertEquals("CM", retrievedList.get(0).getSymbol());
        
        retrievedList = reg.byIdentifier(
                new Identifier("does not exist"));
        assertEquals(retrievedList.size(), 0);
    }

    @Test
    public void getChord() {
        VoicedChord chord1 = getChord("C", ChordType.TRIAD, Quality.MAJOR_TRIAD);
        reg.add(c1, chord1);
        
        VoicedChord fromReg = reg.getChord(c1, 0);
        assertTrue(fromReg.getSymbol().equals(chord1.getSymbol()));
    }
    
    @Test
    public void set() {
        VoicedChord chord1 = getChord("C", ChordType.TRIAD, Quality.MAJOR_TRIAD);
        reg.add(c1, chord1);
        
        VoicedChord chord2 = getChord("D", ChordType.SEVENTH, Quality.DIMINISHED_SEVENTH);
        reg.set(c1, 0, chord2);
        
        assertTrue(reg.byIdentifier(c1).get(0).getSymbol().equals(chord2.getSymbol()));
    }    

    private VoicedChord getChord(String root, ChordType type, Quality quality) {
        return builder.setRoot(NoteName.forSymbol(root))
                .setChordSpec(struct.getChordSpec(type, quality))
                .setQuality(quality)
                .buildVoicedChord();
    }

}
