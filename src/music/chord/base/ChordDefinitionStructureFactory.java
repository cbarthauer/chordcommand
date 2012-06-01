package music.chord.base;

import java.io.IOException;

import music.chord.grammar.ChordDefinitionLexer;
import music.chord.grammar.ChordDefinitionParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public final class ChordDefinitionStructureFactory {
    public static ChordDefinitionStructure getInstance(String file) 
            throws IOException, RecognitionException {
        CharStream charStream = new ANTLRFileStream(file);
        ChordDefinitionLexer lexer = new ChordDefinitionLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        ChordDefinitionParser parser = new ChordDefinitionParser(tokenStream);
        ChordDefinitionStructure struct = parser.program();     
        return struct;
    }
    
    private ChordDefinitionStructureFactory() {
        //Hide utility class constructor.
    }
}
