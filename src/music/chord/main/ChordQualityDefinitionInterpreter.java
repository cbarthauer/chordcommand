package music.chord.main;

import java.io.IOException;

import music.chord.base.Constants;
import music.chord.base.QualityRegistry;
import music.chord.grammar.ChordQualityDefinitionLexer;
import music.chord.grammar.ChordQualityDefinitionParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class ChordQualityDefinitionInterpreter {

    /**
     * @param args
     * @throws IOException 
     * @throws RecognitionException 
     */
    public static void main(String[] args) throws IOException, RecognitionException {
        CharStream charStream = new ANTLRFileStream(Constants.getChordDefinitions());
        ChordQualityDefinitionLexer lexer = new ChordQualityDefinitionLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        ChordQualityDefinitionParser parser = new ChordQualityDefinitionParser(tokenStream);
        QualityRegistry registry = parser.program();
        System.out.println(registry.toString());
    }
    
}
