package music.chord.engine.protocol;

import static org.junit.Assert.assertEquals;
import music.chord.base.NoteName;
import music.chord.base.Quality;

import org.junit.Before;
import org.junit.Test;

public class RequestBuilderTest {

    private Identifier id;
    private RequestBuilder reqBuilder;
    
    @Before
    public void setUp() throws Exception {
        id = new Identifier("c1");
        reqBuilder = new RequestBuilder(id);
    }

    @Test
    public void chordRequest() {
        ChordRequest request = 
            reqBuilder.chordRequest(
                new ChordPair(NoteName.forSymbol("C"), Quality.MINOR_TRIAD),
                new ChordPair(NoteName.forSymbol("A"), Quality.MINOR_TRIAD),
                new ChordPair(NoteName.forSymbol("B"), Quality.MINOR_TRIAD));
        assertEquals(3, request.getChordPairs().size());
    }
    
    @Test
    public void insertRequest() {
        InsertChordRequest request = reqBuilder.insertRequest(
            1,
            new ChordPair(NoteName.forSymbol("C"), Quality.MINOR_TRIAD),
            new ChordPair(NoteName.forSymbol("A"), Quality.MINOR_TRIAD),
            new ChordPair(NoteName.forSymbol("B"), Quality.MINOR_TRIAD));
        assertEquals(3, request.getChordPairs().size());
        assertEquals(1, request.getPosition());
    }

}
