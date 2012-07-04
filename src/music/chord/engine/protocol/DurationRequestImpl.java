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

import music.chord.base.Duration;

final class DurationRequestImpl implements DurationRequest {
    
    private Identifier id;
    private List<Integer> positions;
    private Duration duration;

    DurationRequestImpl(Identifier id, List<Integer> positions, Duration duration) {
        this.id = id;
        this.positions = positions;
        this.duration = duration;
    }

    public final Duration getDuration() {
        return duration;
    }

    public final Identifier getIdentifier() {
        return id;
    }

    public final List<Integer> getPositions() {
        return new ArrayList<Integer>(positions);
    }
}
