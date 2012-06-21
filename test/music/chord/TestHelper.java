package music.chord;

import java.io.IOException;

import music.chord.arrangement.BuilderFactory;
import music.chord.arrangement.ChordDefinitionStructure;
import music.chord.arrangement.VoicedChord;
import music.chord.arrangement.VoicedChordBuilder;
import music.chord.base.ChordPair;
import music.chord.base.Constants;
import music.chord.base.NoteName;
import music.chord.base.Quality;
import music.chord.grammar.ChordDefinitionStructureFactory;

import org.antlr.runtime.RecognitionException;

public class TestHelper {
    private ChordDefinitionStructure struct;
    private VoicedChordBuilder builder;
    
    public TestHelper() throws IOException, RecognitionException {
        struct = ChordDefinitionStructureFactory.getInstance(Constants.getChordDefinitions());
        builder = BuilderFactory.getTriadBuilder(struct);        
    }
    
    public VoicedChord getChord(String root, Quality quality) {
        return builder.setPair(new ChordPair(NoteName.forSymbol(root), quality))
                .buildVoicedChord();
    }
}
