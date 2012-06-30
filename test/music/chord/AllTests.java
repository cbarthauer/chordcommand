package music.chord;

import music.chord.arrangement.ChordFinderTest;
import music.chord.arrangement.ChordVoicerTest;
import music.chord.arrangement.ClosestVoicingStrategyTest;
import music.chord.arrangement.VoicedChordBuilderTest;
import music.chord.base.QualityRegistryImplTest;
import music.chord.display.FileFormatterTest;
import music.chord.engine.ChordEngineImplTest;
import music.chord.engine.protocol.RequestBuilderTest;
import music.chord.grammar.ChordListRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    ChordEngineImplTest.class,
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
