package music.chord.main;

import java.io.IOException;

import music.chord.grammar.ChordDefinitionLexer;
import music.chord.grammar.ChordDefinitionParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class ChordDefinitionInterpreter {

    /**
     * @param args
     * @throws IOException 
     * @throws RecognitionException 
     */
    public static void main(String[] args) throws IOException, RecognitionException {
        CharStream charStream = new ANTLRFileStream(
                "D:\\musicspace\\chordgrammar\\definitions\\chords.txt");
        ChordDefinitionLexer lexer = new ChordDefinitionLexer(charStream);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        ChordDefinitionParser parser = new ChordDefinitionParser(tokenStream);
        parser.program();
        System.out.println("Done.");
    }

}
