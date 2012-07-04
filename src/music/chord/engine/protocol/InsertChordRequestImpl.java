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

import java.util.List;

import music.chord.arrangement.VoicedChord;

final class InsertChordRequestImpl implements InsertChordRequest {

    private ChordRequest chordRequest;
    private int position;

    InsertChordRequestImpl(
            ChordRequest chordRequest, 
            int position) {

        this.chordRequest = chordRequest;
        this.position = position;
    }

    @Override
    public final List<VoicedChord> getChordList() {
        return chordRequest.getChordList();
    }

    @Override
    public final Identifier getIdentifier() {
        return chordRequest.getIdentifier();
    }

    @Override
    public final int getPosition() {
        return position;
    }

}
