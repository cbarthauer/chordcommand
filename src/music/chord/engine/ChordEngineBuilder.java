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

import music.chord.arrangement.ChordFinder;
import music.chord.arrangement.ChordVoicer;
import music.chord.arrangement.DerivedChordBuilder;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.grammar.ChordListRegistry;

public final class ChordEngineBuilder {
    private ChordEngineBuilder() {
        //Hide utility class constructor.
    }

    public static ChordEngine build(
            VoicedChordBuilder triadBuilder,
            VoicedChordBuilder seventhBuilder, 
            VoicedChordBuilder ninthBuilder,
            ChordVoicer voicer,
            ChordFinder finder,
            QualityRegistry qualities) {
        
        return new ChordEngineImpl(
                triadBuilder, 
                seventhBuilder, 
                ninthBuilder,
                new DerivedChordBuilder(),
                new ChordListRegistry(),
                voicer,
                finder,
                qualities);
    }
}
