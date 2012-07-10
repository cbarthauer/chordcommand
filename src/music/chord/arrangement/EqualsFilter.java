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

import music.chord.base.ChordMember;
import music.chord.base.NoteName;

public final class EqualsFilter implements ChordMemberFilter {

    private ChordMember member;
    private NoteName noteName;

    public EqualsFilter(ChordMember member, NoteName noteName) {
        this.member = member;
        this.noteName = noteName;
    }

    @Override
    public final boolean filter(VoicedChord chord) {
        NoteName current = chord.noteNameFromChordMember(member);
        return noteName.equals(current);
    }

}
