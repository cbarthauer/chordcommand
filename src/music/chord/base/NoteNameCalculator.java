package music.chord.base;


class NoteNameCalculator {
    private static final int DIATONIC_STEPS_IN_OCTAVE = 7;
    private static final int HALF_STEPS_IN_OCTAVE = 12;
    
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
            finalNoteEnum.getDiatonicIndex() == DIATONIC_STEPS_IN_OCTAVE
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
        
        if(finalChromaticIndex > HALF_STEPS_IN_OCTAVE) {
            finalChromaticIndex = finalChromaticIndex % HALF_STEPS_IN_OCTAVE; 
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
