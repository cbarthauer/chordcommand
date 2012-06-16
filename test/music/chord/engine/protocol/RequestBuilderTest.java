package music.chord.engine.protocol;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import music.chord.TestHelper;
import music.chord.arrangement.VoicedChord;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;

import org.junit.Before;
import org.junit.Test;

public class RequestBuilderTest {

    private RequestBuilder reqBuilder;
    private TestHelper helper;
    
    @Before
    public void setUp() throws Exception {
        reqBuilder = new RequestBuilder(new Identifier("c1"));
        helper = new TestHelper();
    }

    @Test
    public void addRequests() {
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(helper.getChord("C", ChordType.TRIAD, Quality.MAJOR_TRIAD));
        AddChordRequest[] requests = reqBuilder.addRequests(chordList);
        assertEquals(requests.length, 1);
        assertEquals(requests[0].getNoteName(), NoteName.forSymbol("C"));
    }

}
