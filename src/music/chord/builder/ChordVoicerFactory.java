package music.chord.builder;

import java.io.IOException;

import music.chord.arrangement.ClosestVoicingStrategy;
import music.chord.arrangement.VoicingManager;
import music.chord.grammar.ChordLexer;
import music.chord.grammar.ChordParser;
import music.chord.grammar.ChordParser.compilationUnit_return;
import music.chord.grammar.ChordWalker;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class ChordVoicerFactory {
	public static ChordVoicer getInstance(String file) throws IOException, RecognitionException {
		CharStream charStream = new ANTLRFileStream(file);
		ChordLexer lexer = new ChordLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.getTree());
		ChordWalker walker = new ChordWalker(nodeStream);
		ChordProgression progression = walker.compilationUnit();
		VoicingManager voicingManager = progression.getVoicingManager();
		
		ChordVoicer voicer = new ChordVoicer(
		    new ClosestVoicingStrategy(new DerivedChordBuilder()),
		    voicingManager
		);		
		
		return voicer;
	}
}
