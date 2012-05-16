package music.chord.base;


class NoteNameCalculator {
    private NoteNameEnum initialNoteEnum;
    private NoteNameEnum finalNoteEnum;
    private int finalChromaticIndex;
    
    NoteNameCalculator(NoteNameEnum noteEnum) {
        this.initialNoteEnum = noteEnum;
    }
    
    private void adjustToChromaticIndex() {
        finalNoteEnum = 
            NoteNameEnum.byIndex(finalNoteEnum.getDiatonicIndex(), finalChromaticIndex);
    }

    private NoteNameEnum nextDiatonic() {
        int diatonicIndex = 
            finalNoteEnum.getDiatonicIndex() == Interval.PERFECT_OCTAVE.getDiatonicSteps()
            ? 1
            : finalNoteEnum.getDiatonicIndex() + 1;
        
        return NoteNameEnum.forDiatonicIndex(diatonicIndex);
    }

    NoteNameEnum result() {
        adjustToChromaticIndex();
        return finalNoteEnum;
    }

    NoteNameCalculator upChromaticBy(int halfSteps) {
        finalChromaticIndex = initialNoteEnum.getChromaticIndex() + halfSteps;
        
        if(finalChromaticIndex > Interval.PERFECT_OCTAVE.getHalfSteps()) {
            finalChromaticIndex = finalChromaticIndex % Interval.PERFECT_OCTAVE.getHalfSteps(); 
        }
        
        return this;
    }
    
    NoteNameCalculator upDiatonicBy(int diatonicSteps) {
        finalNoteEnum = initialNoteEnum;
        
        for(int i = 0; i < diatonicSteps; i++) {
            finalNoteEnum = nextDiatonic();
        }
        
        return this;
    }
    
}
