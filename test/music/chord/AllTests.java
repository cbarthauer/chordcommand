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

import music.chord.arrangement.ChordFinderTest;
import music.chord.arrangement.ChordVoicerTest;
import music.chord.arrangement.ClosestVoicingStrategyTest;
import music.chord.arrangement.VoicedChordBuilderTest;
import music.chord.base.QualityRegistryImplTest;
import music.chord.display.FileFormatterTest;
import music.chord.engine.ChordEngineTest;
import music.chord.engine.protocol.RequestBuilderTest;
import music.chord.grammar.ChordListRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    ChordEngineTest.class,
    ChordFinderTest.class,
    ChordListRegistryTest.class,
    ChordVoicerTest.class,
    ClosestVoicingStrategyTest.class,
    FileFormatterTest.class,
    QualityRegistryImplTest.class,
    RequestBuilderTest.class,
    VoicedChordBuilderTest.class,
})
public class AllTests {

}
