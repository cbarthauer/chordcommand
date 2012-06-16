package music.chord;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordType;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.grammar.ChordDefinitionStructureFactory;

public class TestHelper {
    private ChordDefinitionStructure struct;
    private VoicedChordBuilder builder;
    
    public TestHelper() throws IOException, RecognitionException {
        struct = ChordDefinitionStructureFactory.getInstance("definitions/chords.txt");
        builder = BuilderFactory.getTriadBuilder(struct);        
    }
    
    public VoicedChord getChord(String root, ChordType type, Quality quality) {
        return builder.setRoot(NoteName.forSymbol(root))
                .setChordSpec(struct.getChordSpec(type, quality))
                .setQuality(quality)
                .buildVoicedChord();
    }
}
