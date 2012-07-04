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

import music.chord.arrangement.Voicing;

final class VoicingRequestImpl implements VoicingRequest {

    private Identifier id;
    private ArrayList<Integer> positions;
    private Voicing voicing;

    VoicingRequestImpl(Identifier id, List<Integer> positions, Voicing voicing) {
        this.id = id;
        this.positions = new ArrayList<Integer>(positions);
        this.voicing = voicing;
    }
    
    @Override
    public final Identifier getIdentifier() {
        return id;
    }

    @Override
    public final List<Integer> getPositions() {
        return new ArrayList<Integer>(positions);
    }

    @Override
    public final Voicing getVoicing() {
        return voicing;
    }

}
