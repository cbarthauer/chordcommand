package music.chord.engine.protocol;

import static org.junit.Assert.assertEquals;
import music.chord.TestHelper;

import org.junit.Before;
import org.junit.Test;

public class RequestBuilderTest {

    private Identifier id;
    private RequestBuilder reqBuilder;
    private TestHelper helper;
    
    @Before
    public void setUp() throws Exception {
        id = new Identifier("c1");
        reqBuilder = new RequestBuilder(id);
        helper = new TestHelper();
    }

    @Test
    public void chordRequest() {
        ChordRequest request = 
            reqBuilder.chordRequest(
                helper.getChord("C", "MINOR_TRIAD"),
                helper.getChord("A", "MINOR_TRIAD"),
                helper.getChord("B", "MINOR_TRIAD"));
        assertEquals(3, request.getChordList().size());
    }
    
    @Test
    public void insertRequest() {
        InsertChordRequest request = reqBuilder.insertRequest(
            1,
            helper.getChord("C", "MINOR_TRIAD"),
            helper.getChord("A", "MINOR_TRIAD"),
            helper.getChord("B", "MINOR_TRIAD"));
        assertEquals(3, request.getChordList().size());
        assertEquals(1, request.getPosition());
    }

}
