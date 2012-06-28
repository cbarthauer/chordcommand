package music.chord;

import music.chord.arrangement.ChordFinderTest;
import music.chord.arrangement.QualityVoicedChordBuilderTest;
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
    FileFormatterTest.class,
    QualityRegistryImplTest.class,
    QualityVoicedChordBuilderTest.class,
    RequestBuilderTest.class
})
public class AllTests {

}
