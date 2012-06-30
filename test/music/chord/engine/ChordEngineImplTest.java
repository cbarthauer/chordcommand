package music.chord.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import music.chord.TestHelper;
import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordVoicerFactory;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.QualityRegistryFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.arrangement.Voicing;
import music.chord.arrangement.VoicingFactory;
import music.chord.base.ChordMember;
import music.chord.base.Constants;
import music.chord.base.Duration;
import music.chord.base.NoteName;
import music.chord.engine.protocol.ChordRequest;
import music.chord.engine.protocol.Identifier;
import music.chord.engine.protocol.LoadRequest;
import music.chord.engine.protocol.RequestBuilder;
import music.chord.grammar.ChordListRegistry;

import org.junit.Before;
import org.junit.Test;

public class ChordEngineImplTest {
        
    private ChordEngine engine;
    private Identifier id;
    private RequestBuilder builder;
    private TestHelper helper;
    private ChordRequest request;
    private QualityRegistry qualities;
    
    @Before
    public void setUp() throws Exception {
        qualities = QualityRegistryFactory.getInstance(
                Constants.getChordDefinitions());
        
        VoicedChordBuilder triadBuilder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("MAJOR_TRIAD"));
        VoicedChordBuilder seventhBuilder = BuilderFactory.getSeventhBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_SEVENTH"));
        VoicedChordBuilder ninthBuilder = BuilderFactory.getNinthBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("DOMINANT_NINTH"));
        
        engine = new ChordEngineImpl(
                triadBuilder,
                seventhBuilder,
                ninthBuilder,
                new DerivedChordBuilder(),
                new ChordListRegistry(),
                ChordVoicerFactory.getInstance(qualities),
                qualities);
        
        id = new Identifier("default");
        builder = new RequestBuilder(id);
        helper = new TestHelper();
        
        request = builder.chordRequest(
            helper.getChord("C", "MAJOR_TRIAD"),
            helper.getChord("D", "MINOR_TRIAD"),
            helper.getChord("G", "DOMINANT_SEVENTH"),
            helper.getChord("C", "MAJOR_TRIAD"));
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
            builder.createChordRequest(
                NoteName.forSymbol("C"), 
                qualities.forName("MAJOR_TRIAD")));
        
        assertEquals(NoteName.forSymbol("C"), chord.noteNameFromChordMember(ChordMember.ROOT));
        assertEquals(qualities.forName("MAJOR_TRIAD"), chord.getQuality());
    }
    
    @Test
    public void insertChords() {
        engine.addChords(request)
            .insertChords(
                builder.insertRequest(
                    2, 
                    helper.getChord("Eb", "MINOR_SEVENTH"),
                    helper.getChord("F#", "MINOR_SEVENTH")));
        
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
