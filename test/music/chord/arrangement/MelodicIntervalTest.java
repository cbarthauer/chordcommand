package music.chord.arrangement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import music.chord.AbstractTest;
import music.chord.arrangement.impl.MelodicIntervalImpl;
import music.chord.base.Direction;
import music.chord.base.Interval;
import music.chord.base.NoteName;

import org.junit.Before;
import org.junit.Test;

public class MelodicIntervalTest extends AbstractTest {

    private MelodicInterval upPerfectFifth;
    private MelodicIntervalImpl downMinorSecond;

    @Before
    public void setUp() throws Exception {
        downMinorSecond = new MelodicIntervalImpl(Direction.DOWN, Interval.MINOR_SECOND);
        upPerfectFifth = new MelodicIntervalImpl(Direction.UP, Interval.PERFECT_FIFTH);
    }

    @Test
    public void forNoteName() {
        NoteName c = NoteName.forSymbol("C");
        NoteName b = downMinorSecond.forNoteName(c);
        assertTrue(b != null);
        assertEquals("B", b.getSymbol());
        
        
        NoteName bFlat = NoteName.forSymbol("Bb");
        NoteName f = upPerfectFifth.forNoteName(bFlat);
        assertTrue(f != null);
        assertEquals("F", f.getSymbol());
    }

}
