package music.chord;

import java.io.IOException;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.QualityRegistry;
import music.chord.base.QualityRegistryFactory;

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
