package music.chord.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.Quality;
import music.chord.engine.protocol.AddChordRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.LoadRequest;
import music.chord.engine.protocol.RequestBuilder;
import music.chord.grammar.ChordDefinitionStructureFactory;
import music.chord.grammar.VoicingFactory;

import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChordEngineImplTest {
    private static ChordDefinitionStructure struct;
    
    @BeforeClass
    public static void setUpClass() throws IOException, RecognitionException {
        struct = ChordDefinitionStructureFactory.getInstance(
                "d:/musicspace/chordgrammar/definitions/chords.txt");
    }
    
    private ChordEngine engine;
    private Identifier id;
    private RequestBuilder builder;
    private AddChordRequest[] addRequestList;
    
    @Before
    public void setUp() throws Exception {
        engine = new ChordEngineImpl(
            BuilderFactory.getTriadBuilder(struct),
            BuilderFactory.getSeventhBuilder(struct),
            BuilderFactory.getNinthBuilder(struct),
            struct);
        
        id = new Identifier("default");
        builder = new RequestBuilder(id);
        
        addRequestList = new AddChordRequest[] {
            builder.addRequest("C", Quality.MAJOR_TRIAD),
            builder.addRequest("D", Quality.MINOR_TRIAD),
            builder.addRequest("G", Quality.DOMINANT_SEVENTH),
            builder.addRequest("C", Quality.MAJOR_SEVENTH)};
    }

    @Test
    public void addChords() {        
        engine.addChords(addRequestList);        
        assertEquals(engine.byIdentifier(id).size(), 4);
    }

    @Test
    public void insertChords() {
        builder.setInsertPosition(2);
        
        engine.addChords(addRequestList)
            .insertChords(
                builder.insertRequest("Eb", Quality.MINOR_SEVENTH),
                builder.insertRequest("F#", Quality.MINOR_SEVENTH));
        
        assertEquals("Dm", engine.byIdentifier(id).get(1).getSymbol());
        assertEquals("Ebm7", engine.byIdentifier(id).get(2).getSymbol());
        assertEquals("F#m7", engine.byIdentifier(id).get(3).getSymbol());
        assertEquals("Gdom7", engine.byIdentifier(id).get(4).getSymbol());
    }

    @Test
    public void load() {
        LoadRequest request = builder.loadRequest("examples/out.txt");
        engine.load(request);
        List<VoicedChord> chordList = engine.byIdentifier(id);
        assertTrue(!chordList.isEmpty());
    }
    
    @Test
    public void removeChords() {
        engine.addChords(addRequestList)
            .removeChords(builder.removeRequest(0, 3));
        
        assertEquals("Dm", engine.byIdentifier(id).get(0).getSymbol());
        assertEquals("Gdom7", engine.byIdentifier(id).get(1).getSymbol());
    }

    @Test
    public void setVoicings() {
        Voicing voicing = VoicingFactory.instanceFromChordMemberList(
            ChordMember.THIRD, 
            ChordMember.FIFTH, 
            ChordMember.ROOT, 
            ChordMember.FIFTH);
        builder.setVoicing(voicing);
        
        engine.addChords(addRequestList)
            .setVoicings(builder.voicingRequest(0, 2));
        
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(chordList.get(0).getVoicing(), voicing);
        assertNotSame(chordList.get(1).getVoicing(), voicing);
        assertEquals(chordList.get(2).getVoicing(), voicing);
        assertNotSame(chordList.get(3).getVoicing(), voicing);
    }
    
    @Test
    public void setDurations() {
        builder.setDuration(Duration.HALF);
        engine.addChords(addRequestList)
            .setDurations(builder.durationRequest(1, 3));
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(Duration.QUARTER, chordList.get(0).getDuration());
        assertEquals(Duration.HALF, chordList.get(1).getDuration());
        assertEquals(Duration.QUARTER, chordList.get(2).getDuration());
        assertEquals(Duration.HALF, chordList.get(3).getDuration());
    }
    
    @Test
    public void setOctaves() {
        builder.setOctave(3);
        engine.addChords(addRequestList)
            .setOctaves(builder.octaveRequest(0, 3));
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(3, chordList.get(0).getOctave());
        assertEquals(4, chordList.get(1).getOctave());
        assertEquals(4, chordList.get(2).getOctave());
        assertEquals(3, chordList.get(3).getOctave());
    }
}
