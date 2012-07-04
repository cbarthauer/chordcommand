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
package music.chord.engine;

import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.engine.protocol.CreateChordRequest;

public final class CreateChordRequestImpl implements CreateChordRequest {

    private NoteName root;
    private Quality quality;

    public CreateChordRequestImpl(NoteName root, Quality quality) {
        this.root = root;
        this.quality = quality;
    }

    @Override
    public Quality getQuality() {
        return quality;
    }

    @Override
    public NoteName getRoot() {
        return root;
    }

    @Override
    public ChordType getType() {
        return quality.getType();
    }

}
