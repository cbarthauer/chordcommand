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
package music.chord.base;

public enum Interval {
//	                   chromaticHalfSteps diatonicSteps
    PERFECT_UNISON(    0,                 0),
    MINOR_SECOND(      1,                 1),
    MAJOR_SECOND(      2,                 1),
	MINOR_THIRD(       3,                 2),
	MAJOR_THIRD(       4,                 2),
	PERFECT_FOURTH(    5,                 3),
	AUGMENTED_FOURTH(  6,                 3),
	DIMINISHED_FIFTH(  6,                 4),
	PERFECT_FIFTH(     7,                 4),
	AUGMENTED_FIFTH(   8,                 4),
	MINOR_SIXTH(       8,                 5),
	MAJOR_SIXTH(       9,                 5),
	DIMINISHED_SEVENTH(9,                 6),
	MINOR_SEVENTH(     10,                6),
	MAJOR_SEVENTH(     11,                6),
	PERFECT_OCTAVE(    12,                7), 
	MAJOR_NINTH(       2,                 8);
	
    public static Interval forName(String name) {
        Interval result = null;
        
        for(Interval interval : Interval.values()) {
            if(interval.name().equalsIgnoreCase(name)) {
                result = interval;
                break;
            }
        }
        
        return result;
    }
    
	private int halfSteps;
	private int diatonicSteps;

	private Interval(int halfSteps, int diatonicSteps) {
		this.halfSteps = halfSteps;
		this.diatonicSteps = diatonicSteps;
	}
	
	public int getDiatonicSteps() {
		return diatonicSteps;
	}

	public int getHalfSteps() {
		return halfSteps;
	}
}
