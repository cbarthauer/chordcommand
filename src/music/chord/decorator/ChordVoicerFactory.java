package music.chord.decorator;

import java.io.IOException;

import music.chord.decorator.ChordParser.compilationUnit_return;

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
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.tree);
		ChordWalker walker = new ChordWalker(nodeStream);
		ChordProgression progression = walker.compilationUnit();
		VoicingManager voicingManager = progression.getVoicingManager();
		
		ChordVoicer voicer = new ChordVoicer(
			voicingManager.getTriadVoicingList(), 
			voicingManager.getSeventhVoicingList(),
			new DerivedChordBuilder()
		);		
		
		return voicer;
	}
}
