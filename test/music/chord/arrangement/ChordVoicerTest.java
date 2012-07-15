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

import static org.junit.Assert.assertTrue;

import java.util.List;

import music.chord.AbstractTest;
import music.chord.base.Constants;
import music.chord.base.NoteName;

import org.junit.Before;
import org.junit.Test;

public class ChordVoicerTest extends AbstractTest {

    private ChordVoicer voicer;
    private VoicedChord previousChord;
    private VoicedChord newChord;

    @Before
    public void setUp() throws Exception {
        QualityRegistry qualities = QualityRegistryFactory.getInstance(
            Constants.getChordDefinitions());
        
        voicer = ChordVoicerFactory.getInstance(qualities);
        
        VoicedChordBuilder builder = BuilderFactory.getTriadBuilder(
            NoteName.forSymbol("C"),
            qualities.forName("MAJOR_TRIAD"));
        
        previousChord = builder.buildVoicedChord();
        newChord = builder.setRoot(NoteName.forSymbol("G"))
                .buildVoicedChord();
    }

    @Test
    public void voicingComparisonList() {
        List<VoicingComparison> compList = voicer.voicingComparisonList(
                previousChord, 
                newChord);
        assertTrue(compList.size() > 0);
    }

}
