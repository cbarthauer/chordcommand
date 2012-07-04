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
	MINOR_THIRD(       3,                 2),
	MAJOR_THIRD(       4,                 2),
	PERFECT_FOURTH(    5,                 3),
	DIMINISHED_FIFTH(  6,                 4),
	PERFECT_FIFTH(     7,                 4),
	AUGMENTED_FIFTH(   8,                 4),
	MAJOR_SIXTH(       9,                 5),
	DIMINISHED_SEVENTH(9,                 6),
	MINOR_SEVENTH(     10,                6),
	MAJOR_SEVENTH(     11,                6),
	PERFECT_OCTAVE(    12,                7), 
	MAJOR_NINTH(       2,                 8);
	
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
