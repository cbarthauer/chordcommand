/*******************************************************************************
 * Copyright (c) 2012 Chris Barthauer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Chris Barthauer - initial API and implementation
 ******************************************************************************/
package music.chord.base.impl;

import music.chord.base.Interval;


class NoteNameCalculator {
    private NoteNameEnum initialNoteEnum;
    private NoteNameEnum finalNoteEnum;
    private int finalChromaticIndex;
    
    NoteNameCalculator(NoteNameEnum noteEnum) {
        this.initialNoteEnum = noteEnum;
    }
    
    private void adjustToChromaticIndex() {
        NoteNameEnum tmpEnum  = NoteNameEnum.byIndex(
                finalNoteEnum.getDiatonicIndex(), finalChromaticIndex);
        
        if(tmpEnum == null) {
            /*
             * NoteNameEnum.byIndex(...) could return null because system doesn't 
             * support triple sharps.  By incrementing the diatonic index, 
             * we convert a triple sharp into its enharmonic equivalent from 
             * the next note class (e.g. G### turns into A#).
             */
            int diatonicIndex = nextDiatonicIndex(finalNoteEnum.getDiatonicIndex());
            tmpEnum  = NoteNameEnum.byIndex(diatonicIndex, finalChromaticIndex);
        }
        
        if(tmpEnum == null) {
            //Okay, now for triple flats...
            int diatonicIndex = previousDiatonicIndex(finalNoteEnum.getDiatonicIndex());
            tmpEnum  = NoteNameEnum.byIndex(diatonicIndex, finalChromaticIndex);
        }
        
        finalNoteEnum = tmpEnum;
    }

    private int nextDiatonicIndex(int beginningIndex) {
        int diatonicIndex = 
            beginningIndex == Interval.PERFECT_OCTAVE.getDiatonicSteps()
            ? 1
            : beginningIndex + 1;
        
        return diatonicIndex;
    }

    private NoteNameEnum nextDiatonicNoteNameEnum() {
        int diatonicIndex = nextDiatonicIndex(finalNoteEnum.getDiatonicIndex());        
        return NoteNameEnum.forDiatonicIndex(diatonicIndex);
    }

    private int previousDiatonicIndex(int beginningIndex) {
        int diatonicIndex =
            beginningIndex == 1
            ? Interval.PERFECT_OCTAVE.getDiatonicSteps()
            : beginningIndex - 1;
        return diatonicIndex;
    }

    private NoteNameEnum previousDiatonicNoteNameEnum() {
        int diatonicIndex = previousDiatonicIndex(finalNoteEnum.getDiatonicIndex());        
        return NoteNameEnum.forDiatonicIndex(diatonicIndex);
    }

    NoteNameCalculator downChromaticBy(int halfSteps) {
        finalChromaticIndex = initialNoteEnum.getChromaticIndex() - halfSteps;
        
        if(finalChromaticIndex < 1) {
            finalChromaticIndex = 
                    Math.abs(finalChromaticIndex) % Interval.PERFECT_OCTAVE.getHalfSteps();
            finalChromaticIndex = Interval.PERFECT_OCTAVE.getHalfSteps() - finalChromaticIndex;
        }
        
        return this;
    }
    
    NoteNameCalculator downDiatonicBy(int diatonicSteps) {
        finalNoteEnum = initialNoteEnum;
        
        for(int i = 0; i < diatonicSteps; i++) {
            finalNoteEnum = previousDiatonicNoteNameEnum();
        }
        
        return this;
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
            finalNoteEnum = nextDiatonicNoteNameEnum();
        }
        
        return this;
    }
    
}
