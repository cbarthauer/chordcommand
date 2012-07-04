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
package music.chord.engine.protocol;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.VoicedChord;

final class ChordRequestImpl implements ChordRequest {

    private Identifier id;
    private List<VoicedChord> chordList;

    ChordRequestImpl(Identifier id, List<VoicedChord> chordList) {
        this.id = id;
        this.chordList = new ArrayList<VoicedChord>(chordList);
    }

    public final List<VoicedChord> getChordList() {
        return new ArrayList<VoicedChord>(chordList);
    }

    public final Identifier getIdentifier() {
        return id;
    }

}
