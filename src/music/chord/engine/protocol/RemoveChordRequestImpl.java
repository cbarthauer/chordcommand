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

final class RemoveChordRequestImpl implements RemoveChordRequest {

    private Identifier identifier;
    private List<Integer> positions;

    RemoveChordRequestImpl(Identifier identifier, List<Integer> positions) {
        this.identifier = identifier;
        this.positions = positions;
    }

    @Override
    public final Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public final List<Integer> getPositions() {
        return positions;
    }

}
