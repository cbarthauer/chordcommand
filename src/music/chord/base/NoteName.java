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

import java.util.List;

public abstract class NoteName {    
	private static NoteNameBuilder builder;

	public static List<NoteName> all() {
	    return builder.all();
	}
	
	public static void setNoteNameBuilder(NoteNameBuilder builder) {
	    NoteName.builder = builder;
	}
	
	public static NoteName forSymbol(String symbol) {
	    return builder.forSymbol(symbol);
	}

	public abstract int getChromaticIndex();
	public abstract int getDiatonicIndex();
	public abstract String getSymbol();
	public abstract String name();
	public abstract NoteName up(Interval interval);
}
