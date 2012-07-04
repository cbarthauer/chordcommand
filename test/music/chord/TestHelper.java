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
package music.chord;

import java.io.IOException;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.QualityRegistryFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.Constants;
import music.chord.base.NoteName;

import org.antlr.runtime.RecognitionException;

public class TestHelper {
    private QualityRegistry qualities;
    private VoicedChordBuilder builder;
    
    public TestHelper() throws IOException, RecognitionException {
        qualities = QualityRegistryFactory.getInstance(Constants.getChordDefinitions());
        builder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"),
                qualities.forName("MAJOR_TRIAD"));        
    }
    
    public VoicedChord getChord(String root, String quality) {
        return builder.setRoot(NoteName.forSymbol(root))
                .setQuality(qualities.forName(quality))
                .buildVoicedChord();
    }
}
