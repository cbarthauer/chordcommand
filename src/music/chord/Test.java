package music.chord;

import java.util.List;

import music.chord.ChordParser.compilationUnit_return;

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
	 */
	public static void main(String[] args) throws RecognitionException {
		CharStream charStream = new ANTLRStringStream("sequence { F#m, BM, GM, CM, Fm, Bbm, EbM, AbM }");
//		CharStream charStream = new ANTLRStringStream("sequence { CM, C#M, DM, EbM, EM, FM, F#M, GM, AbM, AM, BbM, BM }");
		ChordLexer lexer = new ChordLexer(charStream );
		TokenStream tokenStream = new CommonTokenStream(lexer);
		ChordParser parser = new ChordParser(tokenStream);
		compilationUnit_return compilationUnit = parser.compilationUnit();
		System.out.println(compilationUnit.tree.toStringTree());
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(compilationUnit.tree);
		ChordWalker walker = new ChordWalker(nodeStream);
		List<Chord> chordList = walker.sequence();
		
//		System.out.println(chordList);
		ChordPlayer.play(chordList);
	}

}
