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

import music.chord.base.ChordType;
import music.chord.base.Quality;

public interface QualityRegistry {
    public List<Quality> all();
    public List<Quality> all(ChordType type);
    public boolean contains(Quality quality);
    public Quality forName(String name);
    public List<Voicing> forQuality(Quality quality);
    public List<Voicing> getCongruentVoicings(Voicing voicing);
}
