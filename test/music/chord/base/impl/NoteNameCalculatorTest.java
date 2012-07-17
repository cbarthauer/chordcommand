package music.chord.base.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NoteNameCalculatorTest {

    @Test
    public void downChromaticBy() {
        NoteNameCalculator calc = new NoteNameCalculator(NoteNameEnum.D_FLAT);
        calc.downChromaticBy(1)
            .downDiatonicBy(1);
        assertEquals(NoteNameEnum.C, calc.result());
        
        calc = new NoteNameCalculator(NoteNameEnum.C);
        calc.downChromaticBy(1)
            .downDiatonicBy(1);
        assertEquals(NoteNameEnum.B, calc.result());
        
        calc = new NoteNameCalculator(NoteNameEnum.C);
        calc.downChromaticBy(12)
            .downDiatonicBy(7);
        assertEquals(NoteNameEnum.C, calc.result());
        
        calc = new NoteNameCalculator(NoteNameEnum.C);
        calc.downChromaticBy(13)
            .downDiatonicBy(8);
        assertEquals(NoteNameEnum.B, calc.result());
    }

}
