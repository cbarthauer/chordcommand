package music.chord;

import java.io.IOException;
import java.util.List;

import music.chord.ChordParser.compilationUnit_return;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class Test {

	/**
	 * @param args
	 * @throws RecognitionException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws RecognitionException, IOException {
		CharStream charStream = new ANTLRFileStream("D:\\musicspace\\chordgrammar\\examples\\test.txt");
//		CharStream charStream = new ANTLRStringStream("progression { F#m, BM, GM, CM, Fm, Bbm, EbM, AbM }");
//		CharStream charStream = new ANTLRStringStream("progression { CM, C#M, DM, EbM, EM, FM, F#M, GM, AbM, AM, BbM, BM }");
		ChordLexer lexer = new ChordLexer(charStream );
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		System.out.println(compilationUnit.tree.toStringTree());
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.tree);
		ChordWalker walker = new ChordWalker(nodeStream);
		ChordGrammarFile file = walker.compilationUnit();
		List<Chord> chordList = file.getChordList();
		VoicingManager voicingManager = file.getVoicingManager();
		
		System.out.println(voicingManager);
		ChordPlayer.play(chordList);
	}

}
