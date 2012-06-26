package music.chord;

import music.chord.arrangement.ChordFinderTest;
import music.chord.arrangement.QualityVoicedChordBuilderTest;
import music.chord.base.QualityRegistryTest;
import music.chord.display.FileFormatterTest;
import music.chord.engine.ChordEngineImplTest;
import music.chord.engine.protocol.RequestBuilderTest;
import music.chord.grammar.ChordListRegistryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    ChordFinderTest.class, 
    FileFormatterTest.class,
    ChordEngineImplTest.class,
    ChordListRegistryTest.class,
    RequestBuilderTest.class,
    QualityRegistryTest.class,
    QualityVoicedChordBuilderTest.class
})
public class AllTests {

}
