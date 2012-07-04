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
package music.chord.display;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.QualityRegistry;
import music.chord.arrangement.QualityRegistryFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.Constants;
import music.chord.base.NoteName;

import org.junit.BeforeClass;
import org.junit.Test;

public class FileFormatterTest {
    private static VoicedChordBuilder triadBuilder;
    private static QualityRegistry qualities;

    @BeforeClass
    public static void setUp() throws Exception {
        qualities = QualityRegistryFactory.getInstance(Constants.getChordDefinitions());
        triadBuilder = BuilderFactory.getTriadBuilder(
                NoteName.forSymbol("C"), 
                qualities.forName("MAJOR_TRIAD"));
    }

    @Test
    public void testFormat() {
        List<VoicedChord> chordList = new ArrayList<VoicedChord>();
        chordList.add(majorTriad(NoteName.forSymbol("C")));
        chordList.add(majorTriad(NoteName.forSymbol("F")));
        chordList.add(majorTriad(NoteName.forSymbol("G")));
        chordList.add(majorTriad(NoteName.forSymbol("C")));
        
        ChordListFormatter formatter = new FileFormatter();
        String result = formatter.format(chordList);
        
        assertTrue(result.length() > 0);
    }

    private VoicedChord majorTriad(NoteName root) {
        return triadBuilder.setRoot(root)
                .setQuality(qualities.forName("MAJOR_TRIAD"))
                .buildVoicedChord();
    }

}
