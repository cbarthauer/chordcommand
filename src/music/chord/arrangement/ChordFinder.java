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
package music.chord.arrangement;

import java.util.List;

import music.chord.base.NoteName;

public interface ChordFinder {
    public List<VoicedChord> find(ChordMemberFilter filter);
    public List<VoicedChord> find(List<NoteName> noteList);
}
