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

public interface Quality {
    public boolean equals(Object obj);
    public String getName();
    public NoteName getNoteName(NoteName root, ChordMember member);
    public String getSymbol();
    public ChordType getType();
    public int hashCode();
}
