package music.chord.base.impl;

import music.chord.base.Interval;
import music.chord.base.NoteName;

public final class NoteNameImpl extends NoteName {
    private static class MapOperation {
        static boolean equals(NoteNameImpl noteName, Object obj) {
            if (noteName == obj)
                return true;
            if (obj == null)
                return false;
            if (noteName.getClass() != obj.getClass())
                return false;
            NoteNameImpl other = (NoteNameImpl) obj;
            if (noteName.noteEnum != other.noteEnum)
                return false;
            return true;           
        }
        
        static int hashCode(NoteNameImpl noteName) {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((noteName.noteEnum == null) 
                            ? 0 
                            : noteName.noteEnum.hashCode());
            return result;
        }
    }
    
    private NoteNameEnum noteEnum;
    private NoteNameCalculator calculator;

    public NoteNameImpl(NoteNameEnum noteNameEnum) {
        this.noteEnum = noteNameEnum;
        this.calculator = new NoteNameCalculator(noteEnum);
    }
    
    @Override
    public final NoteName down(Interval interval) {
        NoteNameEnum noteEnum = calculator.downChromaticBy(interval.getHalfSteps())
            .downDiatonicBy(interval.getDiatonicSteps())
            .result();
        NoteNameImpl noteName = new NoteNameImpl(noteEnum);
        return noteName;
    }

    @Override
    public final boolean equals(Object obj) {
        return MapOperation.equals(this, obj);
    }
    
    @Override
    public final int getChromaticIndex() {
        return noteEnum.getChromaticIndex();
    }
    
    @Override
    public final String getSymbol() {
        return noteEnum.getSymbol();
    }

    @Override
    public final int hashCode() {
        return MapOperation.hashCode(this);
    }

    @Override
    public final String toString() {
        return noteEnum.getSymbol();
    }

    @Override
    public final NoteNameImpl up(Interval interval) {
        NoteNameEnum noteEnum = calculator.upChromaticBy(interval.getHalfSteps())
            .upDiatonicBy(interval.getDiatonicSteps())
            .result();
        NoteNameImpl noteName = new NoteNameImpl(noteEnum);
        return noteName;
    }
}
