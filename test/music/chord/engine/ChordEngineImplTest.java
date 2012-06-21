package music.chord.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import music.chord.TestHelper;
import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.Voicing;
import music.chord.base.ChordMember;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.ChordPair;
import music.chord.engine.protocol.ChordRequest;
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
    private TestHelper helper;
    private ChordRequest request;
    
    @Before
    public void setUp() throws Exception {
        engine = new ChordEngineImpl(
            BuilderFactory.getTriadBuilder(struct),
            BuilderFactory.getSeventhBuilder(struct),
            BuilderFactory.getNinthBuilder(struct),
            ChordVoicerFactory.getInstance(struct),
            struct);
        
        id = new Identifier("default");
        builder = new RequestBuilder(id);
        helper = new TestHelper();
        
        request = builder.chordRequest(
            helper.getChord("C", Quality.MAJOR_TRIAD),
            helper.getChord("D", Quality.MINOR_TRIAD),
            helper.getChord("G", Quality.DOMINANT_SEVENTH),
            helper.getChord("C", Quality.MAJOR_TRIAD));
    }
    
    @Test
    public void addChords() {      
        engine.addChords(request);
        List<VoicedChord> chordList = engine.byIdentifier(id);
        assertEquals(4, chordList.size());
    }
    
    @Test
    public void createChord() {
        VoicedChord chord = engine.createChord(
            new ChordPair(NoteName.forSymbol("C"), Quality.MAJOR_TRIAD));
        assertEquals(NoteName.forSymbol("C"), chord.noteNameFromChordMember(ChordMember.ROOT));
        assertEquals(Quality.MAJOR_TRIAD, chord.getQuality());
    }
    
    @Test
    public void insertChords() {
        engine.addChords(request)
            .insertChords(
                builder.insertRequest(
                    2, 
                    helper.getChord("Eb", Quality.MINOR_SEVENTH),
                    helper.getChord("F#", Quality.MINOR_SEVENTH)));
        
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
        engine.addChords(request)
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
        
        engine.addChords(request)
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
        engine.addChords(request)
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
        engine.addChords(request)
            .setOctaves(builder.octaveRequest(0, 3));
        List<VoicedChord> chordList = engine.byIdentifier(id);
        
        assertEquals(3, chordList.get(0).getOctave());
        assertEquals(4, chordList.get(1).getOctave());
        assertEquals(4, chordList.get(2).getOctave());
        assertEquals(3, chordList.get(3).getOctave());
    }
    
    @Test
    public void voiceAll() {
        engine.addChords(request)
            .voiceAll(builder.voiceAllRequest());
        List<VoicedChord> chordList = engine.byIdentifier(id);
        assertNotSame(chordList.get(2).getVoicing(), chordList.get(3).getVoicing());
    }
}
