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

/**
 * Represents the name of a musical note as well as its 
 * position in the chromatic scale.
 * 
 * <p>Implementations should be immutable, and they 
 * should support at least double-sharps and 
 * double-flats.</p>
 * 
 * @author cbarthauer
 *
 */
public abstract class NoteName {    
	private static NoteNameBuilder builder;

	/**
	 * Returns a list of all NoteNames supported by the 
	 * implementation.
	 * 
     * <p>There are no post-conditions for this
     * method, nor are there side effects of invoking it.</p> 
	 * 
	 * @return List of all supported NoteNames
	 * @throws NullPointException if {@link #setNoteNameBuilder(NoteNameBuilder)}
	 * has not been previously invoked sometime during program execution
	 */
	public static List<NoteName> all() {
	    return builder.all();
	}
	
	/**
	 * Specify which NoteNameBuilder should be used as the NoteName
	 * factory.
	 * 
	 * <p>This method must be invoked at least once per program execution
	 * before calling either all() or forSymbol().</p>
	 * 
	 * @param builder The instance of NoteNameBuilder which will be
	 * used as the NoteName factory
	 */
	public static void setNoteNameBuilder(NoteNameBuilder builder) {
	    NoteName.builder = builder;
	}
	
	/**
	 * Returns the NoteName corresponding to the given symbol.
	 * 
     * <p>The system must invoke {@link #setNoteNameBuilder(NoteNameBuilder) 
     * setNoteNameBuilder} once per program execution prior to invoking this 
     * method.</p> 
	 * 
	 * @param symbol corresponding to the desired NoteName
	 * @return NoteName corresponding to the given symbol
	 * @throws NullPointerException if {@link #setNoteNameBuilder(NoteNameBuilder) 
     * setNoteNameBuilder} has not been previously invoked sometime during program 
     * execution
	 * @throws SymbolNotFoundException if a NoteName corresponding to
	 * the given symbol does not exist
	 */
	public static NoteName forSymbol(String symbol) {
	    return builder.forSymbol(symbol);
	}

	/**
	 * Returns the int value representing the position of this NoteName 
	 * in the chromatic scale.
	 * 
	 * <p>The chromatic scale is comprised of twelve half-steps. Each
	 * half-step in the scale is represented by a position within
	 * the range 1-12 inclusive. Each NoteName occurs at one of 
	 * these positions, starting with C at position 1 and ending 
	 * with B at position 12. Enharmonic spellings of a NoteName
	 * are mapped to the same position in the scale. For example,
	 * both F# and Gb occur at position 7.</p> 
	 * 
	 * <p>There are no pre-conditions or post-conditions for this
	 * method, nor are there side effects of invoking it.</p> 
	 * 
	 * @return int value representing this NoteName's position in 
	 * the chromatic scale; range is 1-12 inclusive
	 */
	public abstract int getChromaticIndex();
	
	
	/**
	 * Returns a String representation of this NoteName.
	 * 
	 * <p>For example, an implementation might return 'C#'
	 * to represent C-sharp, or 'Db' to represent
	 * D-flat.</p>
	 * 
     * <p>There are no pre-conditions or post-conditions for this
     * method, nor are there side effects of invoking it.</p> 
     * 
	 * @return String representation of this NoteName
	 */
	public abstract String getSymbol();
	
	
	/**
	 * Returns the NoteName occurring at the specified interval
	 * away from this NoteName in the ascending direction.
	 * 
     * <p>There are no pre-conditions or post-conditions for this
     * method, nor are there side effects of invoking it.</p>
     * 
	 * @param interval Desired distance from this NoteName in the
	 * ascending direction
	 * 
	 * @return NoteName which occurs at the specified interval
	 * above this NoteName 
	 */
	public abstract NoteName up(Interval interval);
}
